package bankingSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

 class BankingSwing {

    private static final String FILE_NAME = "users.dat";

    // backend storage 
    private static List<User> users = new ArrayList<>();
    private static final Admin admin = new Admin("Admin", "admin@bank.com", "admin123", 0);

    // UI
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JLabel statusLabel; // shows current user (only graphics ig)

    // currently authenticated user (backend variable - unchanged)
    private User currentUser;

    // Colors & fonts
    private final Color BG = new Color(245, 247, 250);
    private final Color CARD = new Color(255, 255, 255);
    private final Color ACCENT = new Color(33, 150, 243);
    private final Color ACCENT_DARK = new Color(21, 101, 192);
    private final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 26);
    private final Font SUB_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font BTN_FONT = new Font("Segoe UI", Font.BOLD, 14);

    public static void main(String[] args) {
        loadUserData();
        SwingUtilities.invokeLater(() -> new BankingSwing().createGUI());
    }
    // Main Ui shuru hoilo -->
    private void createGUI() {
        frame = new JFrame("Banking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(760, 520);
        frame.getContentPane().setBackground(BG);
        frame.setMinimumSize(new Dimension(720, 480));

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(BG);

        // pages
        mainPanel.add(mainMenuPanel(), "MainMenu");
        mainPanel.add(registerPanel(), "Register");
        mainPanel.add(userMenuPanel(), "UserMenu");
        mainPanel.add(adminMenuPanel(), "AdminMenu");

        // status bar (User logged in or out?)
        statusLabel = new JLabel(" Not logged in ");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        statusLabel.setForeground(Color.DARK_GRAY);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG);
        root.add(mainPanel, BorderLayout.CENTER);

        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBorder(new EmptyBorder(8, 12, 8, 12));
        statusBar.setBackground(BG);
        statusBar.add(statusLabel, BorderLayout.WEST);

        root.add(statusBar, BorderLayout.SOUTH);

        frame.setContentPane(root);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // ---------------- helpers (styling) ----------------

    private JPanel cardContainer(LayoutManager lm) {
        JPanel p = new JPanel(lm);
        p.setBackground(CARD);
        p.setBorder(new EmptyBorder(28, 28, 28, 28));
        return p;
    }

    private void styleLabel(JLabel lbl, Font f) {
        lbl.setFont(f);
    }

    private JButton accentButton(String text) {
        JButton b = new JButton(text);
        b.setFont(BTN_FONT);
        b.setBackground(ACCENT);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(220, 42));
        
        //hover on mouse
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b.setBackground(ACCENT_DARK);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                b.setBackground(ACCENT);
            }
        });
        return b;
    }
    // Lekhar jayga
    private JTextField largeField() {
        JTextField tf = new JTextField();
        tf.setFont(SUB_FONT);
        tf.setPreferredSize(new Dimension(360, 36));
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(6, 8, 6, 8)
        ));
        return tf;
    }

    private JPasswordField largePassField() {
        JPasswordField pf = new JPasswordField();
        pf.setFont(SUB_FONT);
        pf.setPreferredSize(new Dimension(360, 36));
        pf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(6, 8, 6, 8)
        ));
        return pf;
    }

    private JLabel fieldLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        return l;
    }

    private JPanel wrapCenter(JPanel inner) {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(BG);
        wrapper.add(inner);
        return wrapper;
    }

    // ---------------- panels ----------------

    private JPanel mainMenuPanel() {
        JPanel p = cardContainer(new BorderLayout());
        p.setPreferredSize(new Dimension(620, 380));

        // title area
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(CARD);
        JLabel title = new JLabel("Welcome to SimpleBank", SwingConstants.LEFT);
        styleLabel(title, TITLE_FONT);
        top.add(title, BorderLayout.WEST);

        JLabel subtitle = new JLabel("Clean, safe, and simple banking demo", SwingConstants.LEFT);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(Color.DARK_GRAY);
        top.add(subtitle, BorderLayout.SOUTH);
        top.setBorder(new EmptyBorder(2, 2, 18, 2));

        p.add(top, BorderLayout.NORTH);

        // center - actions
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(CARD);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        //Main MENU SHOW
        JButton registerBtn = accentButton("Register as User");
        JButton loginUserBtn = accentButton("Login as User");
        JButton loginAdminBtn = accentButton("Login as Admin");
        JButton exitBtn = accentButton("Exit");

        center.add(registerBtn, gbc);
        gbc.gridy++;
        center.add(loginUserBtn, gbc);
        gbc.gridy++;
        center.add(loginAdminBtn, gbc);
        gbc.gridy++;
        center.add(exitBtn, gbc);

        p.add(center, BorderLayout.CENTER);

        // actions
        registerBtn.addActionListener(e -> cardLayout.show(mainPanel, "Register"));
        loginUserBtn.addActionListener(e -> showUserLoginDialog());
        loginAdminBtn.addActionListener(e -> showAdminLoginDialog());
        exitBtn.addActionListener(e -> {
        	//close korle age file save hobe then exit
            saveUserData();
            frame.dispose();
        });

        return wrapCenter(p);
    }

    private JPanel registerPanel() {
        // Using GridBag for roomy inputs
        JPanel form = cardContainer(new GridBagLayout());
        form.setPreferredSize(new Dimension(660, 420));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel title = new JLabel("Create a User Account");
        styleLabel(title, TITLE_FONT);
        gbc.gridwidth = 2;
        gbc.gridy = 0;
        form.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        gbc.gridy++;
        gbc.gridx = 0;
        form.add(fieldLabel("Full Name"), gbc);
        gbc.gridx = 1;
        JTextField nameField = largeField();
        form.add(nameField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        form.add(fieldLabel("Email"), gbc);
        gbc.gridx = 1;
        JTextField emailField = largeField();
        form.add(emailField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        form.add(fieldLabel("Password"), gbc);
        gbc.gridx = 1;
        JPasswordField passField = largePassField();
        form.add(passField, gbc);

        // buttons row
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel btnRow = new JPanel();
        btnRow.setBackground(CARD);
        btnRow.setBorder(new EmptyBorder(12, 0, 0, 0));

        JButton submit = accentButton("Register");
        JButton back = accentButton("Back");
        back.setBackground(new Color(220, 220, 220));
        back.setForeground(Color.DARK_GRAY);

        btnRow.add(submit);
        btnRow.add(Box.createRigidArea(new Dimension(18, 0)));
        btnRow.add(back);

        form.add(btnRow, gbc);

        // actions (logic unchanged)
        submit.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passField.getPassword()).trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required!");
                return;
            }

            // basic duplicate email check
            for (User u : users) {
                if (u.getEmail().equalsIgnoreCase(email)) {
                    JOptionPane.showMessageDialog(frame, "Email already registered.");
                    return;
                }
            }

            int id = users.size() + 1;
            users.add(new User(name, email, password, id));
            saveUserData();

            JOptionPane.showMessageDialog(frame, "Account created! Your ID: " + id);

            nameField.setText("");
            emailField.setText("");
            passField.setText("");

            cardLayout.show(mainPanel, "MainMenu");
        });

        back.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));

        return wrapCenter(form);
    }

    private JPanel userMenuPanel() {
        JPanel p = cardContainer(new BorderLayout());
        p.setPreferredSize(new Dimension(660, 420));

        JLabel title = new JLabel("User Dashboard");
        styleLabel(title, TITLE_FONT);

        p.add(title, BorderLayout.NORTH);

        JPanel actions = new JPanel(new GridBagLayout());
        actions.setBackground(CARD);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(14, 12, 14, 12);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton depositBtn = accentButton("Deposit");
        JButton withdrawBtn = accentButton("Withdraw");
        JButton transferBtn = accentButton("Transfer Money");
        JButton historyBtn = accentButton("View Transaction History");
        JButton balanceBtn = accentButton("View Balance");
        JButton logoutBtn = accentButton("Logout");

        actions.add(depositBtn, gbc);
        gbc.gridx++;
        actions.add(withdrawBtn, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        actions.add(transferBtn, gbc);
        gbc.gridx++;
        actions.add(historyBtn, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        actions.add(balanceBtn, gbc);
        gbc.gridx++;
        actions.add(logoutBtn, gbc);

        p.add(actions, BorderLayout.CENTER);

        // actions (logic unchanged, but use larger custom dialogs)
        depositBtn.addActionListener(e -> showAmountDialog("Deposit", amt -> {
            try {
                if (amt <= 0) throw new IllegalArgumentException("Amount must be positive");
                currentUser.deposit(amt);
                saveUserData();
                JOptionPane.showMessageDialog(frame, "Deposited $" + amt);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid amount!");
            }
        }));

        withdrawBtn.addActionListener(e -> showAmountDialog("Withdraw", amt -> {
            try {
                if (amt <= 0) throw new IllegalArgumentException("Amount must be positive");
                currentUser.withdraw(amt);
                saveUserData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid amount!");
            }
        }));

        transferBtn.addActionListener(e -> showTransferDialog());

        historyBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            for (Transaction t : currentUser.viewTransactions()) {
                sb.append(t.getSummary()).append("\n");
            }
            JOptionPane.showMessageDialog(frame, sb.length() > 0 ? sb.toString() : "No transactions yet");
        });

        balanceBtn.addActionListener(e ->
                JOptionPane.showMessageDialog(frame, "Balance: $" + currentUser.viewBalance())
        );

        logoutBtn.addActionListener(e -> {
            currentUser = null;
            statusLabel.setText(" Not logged in ");
            saveUserData();
            cardLayout.show(mainPanel, "MainMenu");
        });

        return wrapCenter(p);
    }

    private JPanel adminMenuPanel() {
        JPanel p = cardContainer(new BorderLayout());
        p.setPreferredSize(new Dimension(660, 420));

        JLabel title = new JLabel("Admin Panel");
        styleLabel(title, TITLE_FONT);
        p.add(title, BorderLayout.NORTH);

        JPanel actions = new JPanel(new GridBagLayout());
        actions.setBackground(CARD);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(14, 12, 14, 12);

        JButton usersBtn = accentButton("View All Users");
        JButton transBtn = accentButton("View All Transactions");
        JButton deleteBtn = accentButton("Delete User");
        JButton logoutBtn = accentButton("Logout");

        gbc.gridx = 0;
        gbc.gridy = 0;
        actions.add(usersBtn, gbc);
        gbc.gridx++;
        actions.add(transBtn, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        actions.add(deleteBtn, gbc);
        gbc.gridx++;
        actions.add(logoutBtn, gbc);

        p.add(actions, BorderLayout.CENTER);

        usersBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            for (User u : users) sb.append(u).append("\n");
            JOptionPane.showMessageDialog(frame, sb.length() > 0 ? sb.toString() : "No users found.");
        });

        transBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            for (User u : users) {
                for (Transaction t : u.viewTransactions()) {
                    sb.append("User: ").append(u.getName()).append("\n  ").append(t.getSummary()).append("\n");
                }
            }
            JOptionPane.showMessageDialog(frame, sb.length() > 0 ? sb.toString() : "No transactions yet.");
        });

        deleteBtn.addActionListener(e -> {
            String idStr = showSingleInputDialog("Delete user by ID", "User ID:");
            if (idStr != null) {
                try {
                    admin.deleteUser(users, Integer.parseInt(idStr));
                    saveUserData();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid ID!");
                }
            }
        });

        logoutBtn.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));

        return wrapCenter(p);
    }

    // ---------------- custom dialogs & helpers ----------------

    // show a larger modal input dialog for a single value (returns null if cancel)
    private String showSingleInputDialog(String title, String label) {
        final JDialog d = new JDialog(frame, title, true);
        d.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        d.setLayout(new GridBagLayout());
        d.setResizable(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 8, 12);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        d.add(new JLabel(label), gbc);
        gbc.gridy++;
        JTextField input = largeField();
        d.add(input, gbc);

        gbc.gridy++;
        JPanel row = new JPanel();
        row.setBackground(Color.WHITE);
        JButton ok = accentButton("OK");
        JButton cancel = accentButton("Cancel");
        cancel.setBackground(new Color(220, 220, 220));
        cancel.setForeground(Color.DARK_GRAY);

        row.add(ok);
        row.add(Box.createRigidArea(new Dimension(12, 0)));
        row.add(cancel);
        d.add(row, gbc);

        final String[] result = new String[1];
        ok.addActionListener(e -> {
            result[0] = input.getText().trim();
            d.dispose();
        });
        cancel.addActionListener(e -> {
            result[0] = null;
            d.dispose();
        });

        d.pack();
        d.setLocationRelativeTo(frame);
        d.setVisible(true);
        return result[0];
    }

    // amount dialog with callback
    private void showAmountDialog(String title, java.util.function.Consumer<Double> onOk) {
        final JDialog d = new JDialog(frame, title, true);
        d.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        d.setLayout(new GridBagLayout());
        d.setResizable(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 8, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        d.add(new JLabel("Amount:"), gbc);
        gbc.gridy++;
        JTextField amtField = largeField();
        d.add(amtField, gbc);

        gbc.gridy++;
        JPanel row = new JPanel();
        row.setBackground(Color.WHITE);
        JButton ok = accentButton("OK");
        JButton cancel = accentButton("Cancel");
        cancel.setBackground(new Color(220, 220, 220));
        cancel.setForeground(Color.DARK_GRAY);
        row.add(ok);
        row.add(Box.createRigidArea(new Dimension(12, 0)));
        row.add(cancel);
        d.add(row, gbc);

        ok.addActionListener(e -> {
            try {
                double amt = Double.parseDouble(amtField.getText().trim());
                d.dispose();
                onOk.accept(amt);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(d, "Enter a valid numeric amount.");
            }
        });

        cancel.addActionListener(e -> d.dispose());

        d.pack();
        d.setLocationRelativeTo(frame);
        d.setVisible(true);
    }

    private void showTransferDialog() {
        final JDialog d = new JDialog(frame, "Transfer Money", true);
        d.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        d.setLayout(new GridBagLayout());
        d.setResizable(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        d.add(new JLabel("Receiver ID:"), gbc);
        gbc.gridx = 1;
        JTextField idField = largeField();
        d.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        d.add(new JLabel("Amount:"), gbc);
        gbc.gridx = 1;
        JTextField amtField = largeField();
        d.add(amtField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JPanel row = new JPanel();
        row.setBackground(Color.WHITE);
        JButton ok = accentButton("Transfer");
        JButton cancel = accentButton("Cancel");
        cancel.setBackground(new Color(220, 220, 220));
        cancel.setForeground(Color.DARK_GRAY);
        row.add(ok);
        row.add(Box.createRigidArea(new Dimension(12, 0)));
        row.add(cancel);
        d.add(row, gbc);

        ok.addActionListener(ev -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                double amt = Double.parseDouble(amtField.getText().trim());

                Optional<User> receiver = users.stream().filter(u -> u.getId() == id).findFirst();

                if (receiver.isPresent()) {
                    boolean success = currentUser.transfer(receiver.get(), amt); // check if transfer worked
                    saveUserData();
                    
                    if (success) {
                        JOptionPane.showMessageDialog(frame, "Transferred $" + amt + " to " + receiver.get().getName());
                    } else {
                        JOptionPane.showMessageDialog(frame, "Insufficient balance!");
                    }
                    
                    d.dispose();
                } else {
                    JOptionPane.showMessageDialog(d, "User not found!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(d, "Invalid input!");
            }
        });


        cancel.addActionListener(ev -> d.dispose());

        d.pack();
        d.setLocationRelativeTo(frame);
        d.setVisible(true);
    }

    // ---------------- login dialogs (bigger) ----------------

    private void showUserLoginDialog() {
        JDialog d = new JDialog(frame, "User Login", true);
        d.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        d.setLayout(new GridBagLayout());
        d.setResizable(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        d.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        JTextField emailField = largeField();
        d.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        d.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        JPasswordField passField = largePassField();
        d.add(passField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JPanel row = new JPanel();
        row.setBackground(Color.WHITE);
        JButton ok = accentButton("Login");
        JButton cancel = accentButton("Cancel");
        cancel.setBackground(new Color(220, 220, 220));
        cancel.setForeground(Color.DARK_GRAY);
        row.add(ok);
        row.add(Box.createRigidArea(new Dimension(12, 0)));
        row.add(cancel);
        d.add(row, gbc);

        ok.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passField.getPassword()).trim();
            for (User user : users) {
                if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                    currentUser = user;
                    statusLabel.setText(" Logged in as: " + user.getName() + " (ID: " + user.getId() + ") ");
                    cardLayout.show(mainPanel, "UserMenu");
                    d.dispose();
                    return;
                }
            }
            JOptionPane.showMessageDialog(d, "Invalid email or password!");
        });

        cancel.addActionListener(e -> d.dispose());

        d.pack();
        d.setLocationRelativeTo(frame);
        d.setVisible(true);
    }

    private void showAdminLoginDialog() {
        JDialog d = new JDialog(frame, "Admin Login", true);
        d.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        d.setLayout(new GridBagLayout());
        d.setResizable(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        d.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        JTextField emailField = largeField();
        d.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        d.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        JPasswordField passField = largePassField();
        d.add(passField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JPanel row = new JPanel();
        row.setBackground(Color.WHITE);
        JButton ok = accentButton("Login");
        JButton cancel = accentButton("Cancel");
        cancel.setBackground(new Color(220, 220, 220));
        cancel.setForeground(Color.DARK_GRAY);
        row.add(ok);
        row.add(Box.createRigidArea(new Dimension(12, 0)));
        row.add(cancel);
        d.add(row, gbc);

        ok.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passField.getPassword()).trim();
            if (email.equals("admin@bank.com") && password.equals("admin123")) {
                cardLayout.show(mainPanel, "AdminMenu");
                d.dispose();
            } else {
                JOptionPane.showMessageDialog(d, "Invalid admin credentials!");
            }
        });

        cancel.addActionListener(e -> d.dispose());

        d.pack();
        d.setLocationRelativeTo(frame);
        d.setVisible(true);
    }

    // ---------------- persistence (unchanged) ----------------

    private static void saveUserData() {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadUserData() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            users = new ArrayList<>();
            return;
        }

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(file))) {
            users = (ArrayList<User>) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            users = new ArrayList<>();
        }
    }
}
