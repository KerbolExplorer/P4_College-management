package tp.practicas;

/**
 * AVISO: Esta clase puede servir de base para el correspondiente diálogo, pero
 * no es obligatorio su uso, pudiéndose modificar o eliminar sin restricciones.
 */

import javax.swing.*;
import java.awt.*;
class AddStudentDialog extends JDialog{
    private boolean accepted = false;
    private JTextField idField;
    private JTextField nameField;
    
    public AddStudentDialog(Frame owner) {
        super(owner, "Add new Student", true);
        setLayout(new BorderLayout());
        
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Student ID:"));
        idField = new JTextField();
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);
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
    public String getId() {
        return idField.getText().trim();
    }
    public String getName() {
        return nameField.getText().trim();
    }
}
