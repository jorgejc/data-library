
package com.iudigital.view;

import com.iudigital.controller.AuthorController;
import com.iudigital.controller.BookController;
import com.iudigital.controller.CategoryController;
import com.iudigital.domain.Author;
import com.iudigital.domain.Book;
import com.iudigital.domain.Category;
import com.iudigital.exceptions.DatabaseException;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;


public class BookFrame extends javax.swing.JFrame {

    
    private BookController bookController;
    private AuthorController authorController;
    private CategoryController categoryController;
    
    private JTable tableBooks;
    private DefaultTableModel tableModel;
    
    private JTextField txtTitle;
    private JComboBox<Author> cbxAuthor;
    private JComboBox<Category> cbxCategory;
    private JTextField txtYear;
    private JTextField txtIsbn;
    
    private JButton btnSave;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClean;
    
    private Book selectedBook;
    
    public BookFrame() {
        try {
            bookController = new BookController();
            authorController = new AuthorController();
            categoryController = new CategoryController();
            
            configWindow();
            initComponentsNew();
            loadData();
        } catch (Exception e) {
            showError("Error al inicializar la aplicación", e);
            System.exit(1);
        }
    }
    
    private void configWindow() {
        setTitle("Gestión de Libros");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }
    
    private void initComponentsNew() {
        // Panel superior para formulario
        JPanel panelForm = new JPanel(new GridLayout(6, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panelForm.add(new JLabel("Título:"));
        txtTitle = new JTextField();
        panelForm.add(txtTitle);
        
        panelForm.add(new JLabel("Autor:"));
        cbxAuthor = new JComboBox<>();
        panelForm.add(cbxAuthor);
        
        panelForm.add(new JLabel("Categoría:"));
        cbxCategory = new JComboBox<>();
        panelForm.add(cbxCategory);
        
        panelForm.add(new JLabel("Año:"));
        txtYear = new JTextField();
        panelForm.add(txtYear);
        
        panelForm.add(new JLabel("ISBN:"));
        txtIsbn = new JTextField();
        panelForm.add(txtIsbn);
        
        // Panel de botones
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        
        btnSave = new JButton("Guardar");
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveBook();
            }
        });
        panelButtons.add(btnSave);
        
        btnUpdate = new JButton("Actualizar");
        btnUpdate.setEnabled(false);
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBook();
            }
        });
        panelButtons.add(btnUpdate);
        
        btnDelete = new JButton("Eliminar");
        btnDelete.setEnabled(false);
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBook();
            }
        });
        panelButtons.add(btnDelete);
        
        btnClean = new JButton("Limpiar");
        btnClean.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cleanForm();
            }
        });
        panelButtons.add(btnClean);
        
        // Panel norte (formulario + botones)
        JPanel panelNorth = new JPanel(new BorderLayout());
        panelNorth.add(panelForm, BorderLayout.CENTER);
        panelNorth.add(panelButtons, BorderLayout.SOUTH);
        add(panelNorth, BorderLayout.NORTH);
        
        // Panel central (tabla)
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableModel.addColumn("ID");
        tableModel.addColumn("Título");
        tableModel.addColumn("Autor");
        tableModel.addColumn("Categoría");
        tableModel.addColumn("Año");
        tableModel.addColumn("ISBN");
        
        tableBooks = new JTable(tableModel);
        tableBooks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableBooks.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int rowSelected = tableBooks.getSelectedRow();
                if (rowSelected >= 0) {
                    selectBook(rowSelected);
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tableBooks);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel sur (mensajes)
        JPanel panelStatus = new JPanel(new BorderLayout());
        panelStatus.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        JLabel lblStatus = new JLabel("Listo");
        panelStatus.add(lblStatus, BorderLayout.WEST);
        add(panelStatus, BorderLayout.SOUTH);
    }
    
    private void loadData() {
        try {
            // Cargar autores
            List<Author> authors = authorController.getAuthors();
            cbxAuthor.removeAllItems();
            for (Author author : authors) {
                cbxAuthor.addItem(author);
            }
            
            // Cargar categorías
            List<Category> categories = categoryController.getCategories();
            cbxCategory.removeAllItems();
            for (Category category : categories) {
                cbxCategory.addItem(category);
            }
            
            // Cargar libros en la tabla
            loadTableBooks();
            
        } catch (DatabaseException e) {
            showError("Error al cargar datos", e);
        }
    }
    
    private void loadTableBooks() {
        try {
            // Limpiar tabla
            tableModel.setRowCount(0);
            
            // Obtener libros
            List<Book> books = bookController.getBooks();
            
            // Agregar libros a la tabla
            for (Book book : books) {
                Object[] row = new Object[6];
                row[0] = book.getBookId();
                row[1] = book.getTitle();
                row[2] = book.getAuthor();
                row[3] = book.getCategory();
                row[4] = book.getYearPublication();
                row[5] = book.getIsbn();
                
                tableModel.addRow(row);
            }
            
        } catch (DatabaseException e) {
            showError("Error al cargar libros", e);
        }
    }
    
    private void selectBook(int row) {
        try {
            int bookId = (int) tableModel.getValueAt(row, 0);
            selectedBook = bookController.getBook(bookId);
            
            if (selectedBook != null) {
                txtTitle.setText(selectedBook.getTitle());
                txtYear.setText(String.valueOf(selectedBook.getYearPublication()));
                txtIsbn.setText(selectedBook.getIsbn());
                
                // Seleccionar autor y categoría en combos
                for (int i = 0; i < cbxAuthor.getItemCount(); i++) {
                    Author author = cbxAuthor.getItemAt(i);
                    if (author.getAuthorId() == selectedBook.getAuthor().getAuthorId()) {
                        cbxAuthor.setSelectedIndex(i);
                        break;
                    }
                }
                
                for (int i = 0; i < cbxCategory.getItemCount(); i++) {
                    Category category = cbxCategory.getItemAt(i);
                    if (category.getCategoryId()== selectedBook.getCategory().getCategoryId()) {
                        cbxCategory.setSelectedIndex(i);
                        break;
                    }
                }
                
                btnUpdate.setEnabled(true);
                btnDelete.setEnabled(true);
                btnSave.setEnabled(false);
            }
            
        } catch (DatabaseException e) {
            showError("Error al seleccionar libro", e);
        }
    }
    
    private void saveBook() {
        try {
            Book book = getBookFromForm();
            bookController.createBook(book);
            
            JOptionPane.showMessageDialog(this, "Libro guardado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cleanForm();
            loadTableBooks();
            
        } catch (IllegalArgumentException | DatabaseException e) {
            showError("Error al guardar el libro", e);
        }
    }
    
    private void updateBook() {
        try {
            if (selectedBook == null) {
                JOptionPane.showMessageDialog(this, "No hay libro seleccionado para actualizar", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Book book = getBookFromForm();
            book.setBookId(selectedBook.getBookId());
            
            bookController.updateBook(book);
            
            JOptionPane.showMessageDialog(this, "Libro actualizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cleanForm();
            loadTableBooks();
            
        } catch (IllegalArgumentException | DatabaseException e) {
            showError("Error al actualizar el libro", e);
        }
    }
    
    private void deleteBook() {
        try {
            if (selectedBook == null) {
                JOptionPane.showMessageDialog(this, "No hay libro seleccionado para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar el libro '" + selectedBook.getTitle()+ "'?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
            );
            
            if (opcion == JOptionPane.YES_OPTION) {
                bookController.deleteBook(selectedBook.getBookId());
                
                JOptionPane.showMessageDialog(this, "Libro eliminado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cleanForm();
                loadTableBooks();
            }
            
        } catch (DatabaseException e) {
            showError("Error al eliminar el libro", e);
        }
    }
    
    private Book getBookFromForm() {
        Book book = new Book();
        
        book.setTitle(txtTitle.getText().trim());
        book.setAuthor((Author) cbxAuthor.getSelectedItem());
        book.setCategory((Category) cbxCategory.getSelectedItem());
        
        try {
            book.setYearPublication(Integer.parseInt(txtYear.getText().trim()));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El año debe ser un número válido");
        }
        
        book.setIsbn(txtIsbn.getText().trim());
        
        return book;
    }
    
    private void cleanForm() {
        txtTitle.setText("");
        txtYear.setText("");
        txtIsbn.setText("");
        
        if (cbxAuthor.getItemCount() > 0) {
            cbxAuthor.setSelectedIndex(0);
        }
        
        if (cbxCategory.getItemCount() > 0) {
            cbxCategory.setSelectedIndex(0);
        }
        
        selectedBook = null;
        btnSave.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        tableBooks.clearSelection();
    }
    
    private void showError(String mensaje, Exception e) {
        String mensajeError = mensaje + "\n" + e.getClass().getSimpleName();
        if (e.getMessage() != null) {
            mensajeError += ": " + e.getMessage();
        }
        
        JOptionPane.showMessageDialog(this, mensajeError, "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Window".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BookFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BookFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BookFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BookFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BookFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
