package AA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class LibrarySystem extends JFrame implements ActionListener {

    private JLabel bookLbl, authorLbl, dateLbl, publisherLbl;
    private JTextField bookTxt, authorTxt, dateTxt, publisherTxt;
    private JButton addBtn, deleteBtn, saveBtn, cancelBtn, editBtn;
    private ArrayList<Book> books = new ArrayList<>();
    private boolean newBookFlag = false;



    public LibrarySystem() {
        setSize(500, 400);
        setTitle("图书管理系统");

        JPanel bookPanel = new JPanel();
        bookPanel.setLayout(new GridLayout(4, 2));
        bookLbl = new JLabel("书籍名称:");
        bookPanel.add(bookLbl);
        bookTxt = new JTextField(30);
        bookPanel.add(bookTxt);
        authorLbl = new JLabel("作者:");
        bookPanel.add(authorLbl);
        authorTxt = new JTextField(30);
        bookPanel.add(authorTxt);
        dateLbl = new JLabel("出版日期:");
        bookPanel.add(dateLbl);
        dateTxt = new JTextField(30);
        bookPanel.add(dateTxt);
        publisherLbl = new JLabel("出版社:");
        bookPanel.add(publisherLbl);
        publisherTxt = new JTextField(30);
        bookPanel.add(publisherTxt);
        add(bookPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        addBtn = new JButton("添加");
        addBtn.addActionListener(this);
        btnPanel.add(addBtn);
        deleteBtn = new JButton("删除");
        deleteBtn.addActionListener(this);
        btnPanel.add(deleteBtn);
        editBtn = new JButton("编辑");
        editBtn.addActionListener(this);
        btnPanel.add(editBtn);
        saveBtn = new JButton("保存");
        saveBtn.addActionListener(this);
        btnPanel.add(saveBtn);
        cancelBtn = new JButton("取消");
        cancelBtn.addActionListener(this);
        btnPanel.add(cancelBtn);
        add(btnPanel, BorderLayout.SOUTH);

        readLibrary();
        displayBook();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBtn) {
            newBookFlag = true;
            clearFields();
        } else if (e.getSource() == editBtn) {
            int selection = JOptionPane.showConfirmDialog(null,
                    "确定更新此书籍吗？",
                    "Confirm Edit",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (selection == JOptionPane.YES_OPTION) {
                books.remove(getSelectedBook());
                newBookFlag = true;
                displayBook();
            }
        } else if (e.getSource() == deleteBtn) {
            int selection = JOptionPane.showConfirmDialog(null,
                    "确定删除此书籍吗？",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (selection == JOptionPane.YES_OPTION) {
                books.remove(getSelectedBook());
                displayBook();
                writeLibrary();
            }
        } else if (e.getSource() == saveBtn) {
            if (newBookFlag) {
                newBookFlag = false;
                books.add(new Book(bookTxt.getText(),
                        authorTxt.getText(),
                        dateTxt.getText(),
                        publisherTxt.getText()));
            } else {
                Book book = getSelectedBook();
                book.setBookname(bookTxt.getText());
                book.setAuthor(authorTxt.getText());
                book.setDate(dateTxt.getText());
                book.setPublisher(publisherTxt.getText());
            }
            displayBook();
            writeLibrary();
        } else if (e.getSource() == cancelBtn) {
            newBookFlag = false;
            displayBook();
        }
    }

    private void clearFields() {
        bookTxt.setText("");
        bookTxt.setEditable(true);
        authorTxt.setText("");
        dateTxt.setText("");
        publisherTxt.setText("");
    }

    private Book getSelectedBook() {
        JTable table = null;
        int selectedRow = table.getSelectedRow();
        String bookname = (String) table.getValueAt(selectedRow, 0);
        String author = (String) table.getValueAt(selectedRow, 1);
        String date = (String) table.getValueAt(selectedRow, 2);
        String publisher = (String) table.getValueAt(selectedRow, 3);
        return new Book(bookname, author, date, publisher);
    }

    private void displayBook() {
        if (books.size() == 0) {
            clearFields();
            return;
        }
        Book b = getSelectedBook();
        bookTxt.setText(b.getBookname());
        bookTxt.setEditable(false);
        authorTxt.setText(b.getAuthor());
        dateTxt.setText(b.getDate());
        publisherTxt.setText(b.getPublisher());
    }

    private void readLibrary() {
        try {
            Scanner scanner = new Scanner(new File("library.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(",");
                String bookname = tokens[0];
                String author = tokens[1];
                String date = tokens[2];
                String publisher = tokens[3];
                books.add(new Book(bookname, author, date, publisher));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this,
                    "文件未找到:" + e.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
        }

        // 创建JTable并绑定数据
        String[] columns = {"书籍名称", "作者", "出版日期", "出版社"};
        String[][] data = new String[books.size()][4];
        for (int i = 0; i < books.size(); i++) {
            Book b = books.get(i);
            data[i][0] = b.getBookname();
            data[i][1] = b.getAuthor();
            data[i][2] = b.getDate();
            data[i][3] = b.getPublisher();
        }
        JTable table = new JTable(data, columns);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                displayBook();
            }
        });
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.NORTH);
    }

    private void writeLibrary() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("library.txt"));
            for (Book b : books) {
                writer.println(b.getBookname() + "," + b.getAuthor() + "," + b.getDate() + "," + b.getPublisher());
            }
            writer.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "保存文件时出错:" + e.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    }