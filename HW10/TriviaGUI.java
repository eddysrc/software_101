package il.ac.tau.cs.sw1.trivia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class TriviaGUI {

	private static final int MAX_ERRORS = 3;
	private Shell shell;
	private Label scoreLabel;
	private Composite questionPanel;
	private Label startupMessageLabel;
	private Font boldFont;
	private String lastAnswer = "";

	// My added fields:
	private List<String> currentGameQuestions;
	private int score = 0;
	private int wrongAnswersStrike = 0;
	private String currentPath;
	private String currentRightAnswer;
	private int questionsAnswered = 0;
	private boolean freePassUsage = true;
	private boolean freeFiftyUsage = true;
	private boolean game = true;
	
	// Currently visible UI elements.
	Label instructionLabel;
	Label questionLabel;
	private List<Button> answerButtons = new LinkedList<>();
	private Button passButton;
	private Button fiftyFiftyButton;

	public void open() {
		createShell();
		runApplication();
	}

	/**
	 * Creates the widgets of the application main window
	 */
	private void createShell() {
		Display display = Display.getDefault();
		shell = new Shell(display);
		shell.setText("Trivia");

		// window style
		Rectangle monitor_bounds = shell.getMonitor().getBounds();
		shell.setSize(new Point(monitor_bounds.width / 3,
				monitor_bounds.height / 4));
		shell.setLayout(new GridLayout());

		FontData fontData = new FontData();
		fontData.setStyle(SWT.BOLD);
		boldFont = new Font(shell.getDisplay(), fontData);

		// create window panels
		createFileLoadingPanel();
		createScorePanel();
		createQuestionPanel();
	}

	/**
	 * Creates the widgets of the form for trivia file selection
	 */
	private void createFileLoadingPanel() {
		final Composite fileSelection = new Composite(shell, SWT.NULL);
		fileSelection.setLayoutData(GUIUtils.createFillGridData(1));
		fileSelection.setLayout(new GridLayout(4, false));

		final Label label = new Label(fileSelection, SWT.NONE);
		label.setText("Enter trivia file path: ");

		// text field to enter the file path
		final Text filePathField = new Text(fileSelection, SWT.SINGLE
				| SWT.BORDER);
		filePathField.setLayoutData(GUIUtils.createFillGridData(1));

		// "Browse" button
		final Button browseButton = new Button(fileSelection, SWT.PUSH);
		browseButton.setText("Browse");
		browseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String filePath = GUIUtils.getFilePathFromFileDialog(shell);
				filePathField.setText(filePath);
				currentPath = filePath;
			}
		});

		// "Play!" button
		final Button playButton = new Button(fileSelection, SWT.PUSH);
		playButton.setText("Play!");
		playButton.addSelectionListener(new PlayButtonListener());
	}

	/**
	 * Creates the panel that displays the current score
	 */
	private void createScorePanel() {
		Composite scorePanel = new Composite(shell, SWT.BORDER);
		scorePanel.setLayoutData(GUIUtils.createFillGridData(1));
		scorePanel.setLayout(new GridLayout(2, false));

		final Label label = new Label(scorePanel, SWT.NONE);
		label.setText("Total score: ");

		// The label which displays the score; initially empty
		scoreLabel = new Label(scorePanel, SWT.NONE);
		scoreLabel.setLayoutData(GUIUtils.createFillGridData(1));
	}

	/**
	 * Creates the panel that displays the questions, as soon as the game
	 * starts. See the updateQuestionPanel for creating the question and answer
	 * buttons
	 */
	private void createQuestionPanel() {
		questionPanel = new Composite(shell, SWT.BORDER);
		questionPanel.setLayoutData(new GridData(GridData.FILL, GridData.FILL,
				true, true));
		questionPanel.setLayout(new GridLayout(2, true));

		// Initially, only displays a message
		startupMessageLabel = new Label(questionPanel, SWT.NONE);
		startupMessageLabel.setText("No question to display, yet.");
		startupMessageLabel.setLayoutData(GUIUtils.createFillGridData(2));
	}

	/**
	 * Serves to display the question and answer buttons
	 */
	private void updateQuestionPanel(String question, List<String> answers) {
		// Save current list of answers.
		List<String> currentAnswers = answers;
		
		// clear the question panel
		Control[] children = questionPanel.getChildren();
		for (Control control : children) {
			control.dispose();
		}

		// create the instruction label
		instructionLabel = new Label(questionPanel, SWT.CENTER | SWT.WRAP);
		instructionLabel.setText(lastAnswer + "Answer the following question:");
		instructionLabel.setLayoutData(GUIUtils.createFillGridData(2));

		// create the question label
		questionLabel = new Label(questionPanel, SWT.CENTER | SWT.WRAP);
		questionLabel.setText(question);
		questionLabel.setFont(boldFont);
		questionLabel.setLayoutData(GUIUtils.createFillGridData(2));

		// create the answer buttons
		answerButtons.clear();
		for (int i = 0; i < 4; i++) {
			Button answerButton = new Button(questionPanel, SWT.PUSH | SWT.WRAP);
			answerButton.setText(answers.get(i));
			GridData answerLayoutData = GUIUtils.createFillGridData(1);
			answerLayoutData.verticalAlignment = SWT.FILL;
			answerButton.setLayoutData(answerLayoutData);

			answerButton.addSelectionListener(new AnswerButtonListener(answerButton.getText()));
			
			answerButtons.add(answerButton);
		}

		// create the "Pass" button to skip a question
		passButton = new Button(questionPanel, SWT.PUSH);
		passButton.setText("Pass");
		GridData data = new GridData(GridData.END, GridData.CENTER, true,
				false);
		data.horizontalSpan = 1;
		passButton.setLayoutData(data);
		passButton.addSelectionListener(new PassButton());
		
		// create the "50-50" button to show fewer answer options
		fiftyFiftyButton = new Button(questionPanel, SWT.PUSH);
		fiftyFiftyButton.setText("50-50");
		data = new GridData(GridData.BEGINNING, GridData.CENTER, true,
				false);
		data.horizontalSpan = 1;
		fiftyFiftyButton.setLayoutData(data);
		fiftyFiftyButton.addSelectionListener(new FiftyButton());

		// two operations to make the new widgets display properly
		questionPanel.pack();
		questionPanel.getParent().layout();
	}

	/**
	 * Opens the main window and executes the event loop of the application
	 */
	private void runApplication() {
		shell.open();
		Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
		boldFont.dispose();
	}


	// Generates a random question from a given list and shows it to the player, then deletes it.
	private void updateCurrentGame(){
		if (currentGameQuestions.size() == 0) {
			GUIUtils.showInfoDialog(shell, "Game Over", "Your final score is " + score + " after " + questionsAnswered + " questions.");
			reset();
		}
		if (game) {
			// Choosing the question.
			Random rand = new Random();
			int chosenQuestionNumber = rand.nextInt(currentGameQuestions.size());
			String questionToDisplay = currentGameQuestions.get(chosenQuestionNumber);
			currentGameQuestions.remove(chosenQuestionNumber);
			List<String> questionBreakDown = Arrays.asList(questionToDisplay.split("\t").clone());
			String question = questionBreakDown.get(0);
			List<String> answers = questionBreakDown.subList(1, 5);
			currentRightAnswer = answers.get(0);
			Collections.shuffle(answers);
			// Updating panel.
			updateQuestionPanel(question, answers);
			uncoverButtons();
		}
	}

	// Sub method that opens the file and returns a list of it's questions.
	private List<String> createQuestionList(String filePath) throws FileNotFoundException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
		List<String> rtrn = bufferedReader.lines().collect(Collectors.toList());
		return rtrn;
	}

	public class PlayButtonListener implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			game = true;
			updateScore(0);
			try {
				// Creating a list of questions from a file.
				List<String> fileQuestions = createQuestionList(currentPath);
				currentGameQuestions = fileQuestions;
				// Initiating a new game!
				updateCurrentGame();

			} catch (FileNotFoundException fileNotFoundException) {
				GUIUtils.showErrorDialog(shell, "File not found!");
			}
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent selectionEvent) {

		}
	}

	public class AnswerButtonListener implements SelectionListener{

		String value;
		public AnswerButtonListener(String text) {
			value = text;
		}

		@Override
		public void widgetSelected(SelectionEvent selectionEvent) {
			String playerAnswer = this.value;
			questionsAnswered += 1;
			if (playerAnswer.equals(currentRightAnswer)){
				System.out.println("Right answer!");
				updateScore(3);
				wrongAnswersStrike = 0;
			}
			else {
				System.out.println("Wrong answer!");
				updateScore(-1);
				wrongAnswersStrike += 1;
				if (wrongAnswersStrike == 3){
					GUIUtils.showInfoDialog(shell, "Game Over", "Your final score is " + score + " after " + questionsAnswered + " questions.");
					reset();
				}
			}
			if (game) updateCurrentGame();
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent selectionEvent) {

		}
	}

	public class PassButton implements SelectionListener{

		@Override
		public void widgetSelected(SelectionEvent selectionEvent) {
			if (freePassUsage) {
				freePassUsage = false;
				updateCurrentGame();
			} else {
				if (score > 0) {
					updateScore(-1);
					updateCurrentGame();
				}
			}
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent selectionEvent) {

		}
	}

	public class FiftyButton implements SelectionListener{

		@Override
		public void widgetSelected(SelectionEvent selectionEvent) {
			if (freeFiftyUsage) {
				freeFiftyUsage = false;
				coverTwoAnswers();
			} else {
				if (score > 0) {
					updateScore(-1);
					coverTwoAnswers();
				}
			}
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent selectionEvent) {

		}
	}

	private void coverTwoAnswers(){
		Random rand = new Random();
		int i = 0;
		int count = 0;
		for (Button but: answerButtons){
			if (but.getText().equals(currentRightAnswer)) i = count;
			count++;
		}
		int randnum = rand.nextInt(4);
		while (randnum == i){
			randnum = rand.nextInt(4);
		}
		for (int j = 0; j < answerButtons.size(); j++) {
			if (j != randnum && j != i) answerButtons.get(j).setEnabled(false);
		}
	}

	private void uncoverButtons(){
		for (Button but: answerButtons){
			but.setEnabled(true);
		}
	}

	private void reset(){
		score = 0;
		updateScore(0);
		wrongAnswersStrike = 0;
		questionsAnswered = 0;
		updateScore(0);
		freeFiftyUsage = true;
		freePassUsage = true;
		for (Button but: answerButtons){
			but.setEnabled(false);
		}
		game = false;
	}

	private void updateScore(int i){
		score += i;
		scoreLabel.setText(Integer.toString(score));
	}
}