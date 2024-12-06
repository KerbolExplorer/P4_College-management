package tp.practicas;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.UIManager.*;
public class CollegeGUI extends JFrame {
    static private OfferedCourses offeredCourses= new OfferedCourses();
    static private EnrolledStudents enrolledStudents = new EnrolledStudents();

    private JTextArea data;
    private JRadioButton orderName;
    private JRadioButton orderId;
    private JButton addStudent;
    private JButton enrollStudent;

    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem itemAddStudent, itemEnrollStudent, itemExit;

    private void constructMenu() {
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menu = new JMenu("Menu");
        itemAddStudent = new JMenuItem("Add new student");
        itemEnrollStudent = new JMenuItem("Enroll student in course");
        itemExit = new JMenuItem("Exit");
        menu.add(itemAddStudent);
        menu.add(itemEnrollStudent);
        menu.add(itemExit);
        menuBar.add(menu);
        
        itemAddStudent.addActionListener(e -> showAddStudentDialog());
        itemEnrollStudent.addActionListener(e -> showEnrollStudentDialog());
        itemExit.addActionListener(e -> System.exit(0));
    }
    private Component constructControls() {
        orderName = new JRadioButton("Order by student's name", true);
        orderId = new JRadioButton("Order by student's id", false);
        ButtonGroup bg = new ButtonGroup();
        bg.add(orderName);
        bg.add(orderId);
        addStudent = new JButton("Add new student");
        enrollStudent = new JButton("Enroll student in course");
        JPanel east = new JPanel();
        east.setLayout(new BoxLayout(east, BoxLayout.PAGE_AXIS));
        JPanel order = new JPanel();
        order.setLayout(new BoxLayout(order, BoxLayout.PAGE_AXIS));
        order.add(orderName);
        order.add(orderId);
        order.setBorder(BorderFactory.createTitledBorder("List order"));
        east.add(order);
        east.add(addStudent);
        east.add(enrollStudent);
        
        orderName.addActionListener(e -> updateStudentList());
        orderId.addActionListener(e -> updateStudentList());
        addStudent.addActionListener(e -> showAddStudentDialog());
        enrollStudent.addActionListener(e -> showEnrollStudentDialog());
        
        return east;
    }

    private Component constructInfo() {
        data = new JTextArea();
        data.setEditable(false);
        return new JScrollPane(data);
    }

    public CollegeGUI(){
        super("College Management");
        InitialData.init(offeredCourses, enrolledStudents);
        constructMenu();
        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout());
        pane.add(constructInfo(), BorderLayout.CENTER);
        pane.add(constructControls(), BorderLayout.EAST);
        setContentPane(pane);
        setLocation(50,50);
        setSize(700,200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        updateStudentList();
    }
    
    private void updateStudentList() {
        List<Student> students = null;
        if (orderName.isSelected()) {
            students = enrolledStudents.getStudentsOrderByName();
        } else {
            students = enrolledStudents.getStudentsOrderById();
        }
        
        String result = "";
        for (Student student : students) {
            result = result + student + "\n";
        }
        data.setText(result);
    }
    
    private void showAddStudentDialog() {
        AddStudentDialog dialog = new AddStudentDialog(this);
        dialog.setVisible(true);
        if (dialog.isAccepted()) {
            String name = dialog.getName();
            int id;
            try {
                id = Integer.parseInt(dialog.getId());
            } catch(NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid Id format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (enrolledStudents.addStudent(new Student(id, name))) {
                updateStudentList();
            } else {
                JOptionPane.showMessageDialog(this, "Student ID already exists", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showEnrollStudentDialog() {
        EnrollStudentDialog dialog = new EnrollStudentDialog(this, enrolledStudents, offeredCourses);
        dialog.setVisible(true);
        if(dialog.isAccepted()) {
            Student student = dialog.getStudent();
            Course course = dialog.getCourse();
            
            if (student != null && course != null) {
                student.enrollCourse(course);
                updateStudentList();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid selection.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main (String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch(Exception e) {
        }
        new CollegeGUI();
    }
}