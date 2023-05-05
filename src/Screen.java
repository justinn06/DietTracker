import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Screen extends JFrame {
    private JPanel panelMain;
    private JPanel panelLeft;
    private JList JListEntries;
    private JLabel labelNetDiff;
    private JTextField textDate;
    private JTextField textWeight;
    private JButton buttonEdit;
    private JButton buttonSave;
    private JButton buttonDelete;
    private JLabel labelDifferential;
    private JPanel panelRight;

    private ArrayList<Entry> entries;
    private  DefaultListModel ListEntryModel;

    private double differential;
    private double netDifference;

    Screen() {
        super("Diet Tracker");
        this.setContentPane(this.panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setSize(800, 400);

        buttonEdit.setEnabled(false);
        buttonDelete.setEnabled(false);

        entries = new ArrayList<Entry>();
        ListEntryModel = new DefaultListModel();
        JListEntries.setModel(ListEntryModel);

        JListEntries.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int entryNumber = JListEntries.getSelectedIndex();
                if (entryNumber >= 0) {
                    Entry ent = entries.get(entryNumber);
                    textDate.setText(ent.getDate());
                    textWeight.setText(String.valueOf(ent.getWeight()));
                    if (entryNumber > 0) {
                        double x = entries.get(entryNumber).getWeight() - entries.get(entryNumber - 1).getWeight();
                        differential = Math.round((x*100.00))/100.00;
                        labelDifferential.setText(String.valueOf(differential)+" kg");
                    }
                    else
                        labelDifferential.setText("0 kg");
                    buttonEdit.setEnabled(true);
                    buttonDelete.setEnabled(true);
                }
            }
        });

        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Entry entry = new Entry(textDate.getText(), Double.valueOf(textWeight.getText()));
                entries.add(entry);
                refreshEntryList();
            }

        });

        buttonEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int entryNumber = JListEntries.getSelectedIndex();
                if (entryNumber >= 0) {
                    Entry entry = entries.get(entryNumber);
                    entry.setDate(textDate.getText());
                    entry.setWeight(Double.valueOf(textWeight.getText()));
                    refreshEntryList();
                }
            }
        });

        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int personNumber = JListEntries.getSelectedIndex();
                if (personNumber >= 0) {
                    entries.remove(personNumber);
                    refreshEntryList();
                    textDate.setText("");
                    textWeight.setText("");
                    labelDifferential.setText("0 kg");
                }
            }
        });

    }

    public void refreshEntryList() {
        ListEntryModel.removeAllElements();
        System.out.println("Removing all entries from the list.");
        for (Entry e : entries) {
            System.out.println("Adding entry to list: " + e.getDate());
            ListEntryModel.addElement(e.getDate());
        }
        double x = entries.get(entries.size()-1).getWeight() - entries.get(0).getWeight();
        netDifference = Math.round((x*100.0))/100.0;
        System.out.println("Net difference: " + netDifference);
        labelNetDiff.setText(String.valueOf(netDifference)+" kg");
    }

}
