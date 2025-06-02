import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
public class StudentGradeManager extends JFrame {
    private JTextField nameField;
    private JTextField gradesField;
    private JTextArea outputArea;
    private ArrayList<Student> students = new ArrayList<Student>();

    public StudentGradeManager() {
        setTitle("Student Grade Tracker");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Enter Student Details"));

        nameField = new JTextField();
        gradesField = new JTextField();
        JButton addButton = new JButton("Add Student");

        inputPanel.add(new JLabel("Student Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Grades (comma-separated):"));
        inputPanel.add(gradesField);
        inputPanel.add(new JLabel(""));
        inputPanel.add(addButton);
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Summary Report"));
        JButton summaryButton = new JButton("Generate Summary");
        JButton clearButton = new JButton("Clear All");

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(summaryButton);
        bottomPanel.add(clearButton);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String[] gradesText = gradesField.getText().trim().split(",");
                if (name.isEmpty() || gradesText.length == 0) {
                    JOptionPane.showMessageDialog(StudentGradeManager.this,
                            "Please enter both name and grades.");
                    return;
                }
                try {
                    Student student = new Student(name);
                    for (int i = 0; i < gradesText.length; i++) {
                        student.addGrade(Integer.parseInt(gradesText[i].trim()));
                    }
                    students.add(student);
                    outputArea.append("successfully add student "  +"\n");
                    nameField.setText("");
                    gradesField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(StudentGradeManager.this,
                            "Invalid grade input. Use integers separated by commas.");
                }
            }
        });
        summaryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                outputArea.append("\nFinal Report:\n");
                int classHigh = Integer.MIN_VALUE;
                int classLow = Integer.MAX_VALUE;

                for (int i = 0; i < students.size(); i++) {
                    Student s = students.get(i);
                    outputArea.append(s.summary() + "\n");
                    if (s.getHighest() > classHigh) classHigh = s.getHighest();
                    if (s.getLowest() < classLow) classLow = s.getLowest();
                }

                if (!students.isEmpty()) {
                    outputArea.append("\nClass Highest: " + classHigh + "\n");
                    outputArea.append("Class Lowest: " + classLow + "\n");
                } else {
                    outputArea.append("No student data to summarize.\n");
                }
            }
        });
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                students.clear();
                outputArea.setText("");
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new StudentGradeManager();
    }
}