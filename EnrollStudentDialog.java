package tp.practicas;

/**
 * AVISO: Esta clase puede servir de base para el correspondiente diálogo, pero
 * no es obligatorio su uso, pudiéndose modificar o eliminar sin restricciones.
 */

import javax.swing.*;
import java.awt.*;
class EnrollStudentDialog extends JDialog {
    private boolean accepted = false;
    private JComboBox<Student> studentBox;
    private JComboBox<Course> courseBox;
    
    public EnrollStudentDialog(Frame owner, EnrolledStudents enrolledStudents, OfferedCourses offeredCourses) {
        super(owner, "Enroll student in course", true);
        setLayout(new BorderLayout());
        
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Select Student:"));
        studentBox = new JComboBox<>(enrolledStudents.getStudentsOrderById().toArray(new Student[0]));
        inputPanel.add(studentBox);
        inputPanel.add(new JLabel("Selected Course"));
        courseBox = new JComboBox<>(offeredCourses.getCourses().toArray(new Course[0]));
        inputPanel.add(courseBox);
        add(inputPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        JButton acceptButton = new JButton("Accept");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(acceptButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        acceptButton.addActionListener(e -> {
           accepted = true;
           setVisible(false);
        });
        
        cancelButton.addActionListener(e -> setVisible(false));
        
        pack();
    }
    
    public boolean isAccepted() {
        return accepted;
    }
    public Student getStudent() {
        return (Student) studentBox.getSelectedItem();
    }
    public Course getCourse() {
        return (Course) courseBox.getSelectedItem();
    }
}
