package me.donkeycore.chicken;

import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Chicken extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	public static final String version = "0.1";
	private final JTextArea input;
	private final JTextArea output;
	private final JButton totext;
	private final JButton toenc;
	private static final Random rand = new Random();
	private final char[] chicken = "chicken".toCharArray();
	private final char[] CHICKEN = "CHICKEN".toCharArray();
	
	public static void main(String[] args) {
		new Chicken();
	}
	
	public Chicken() {
		setName("Chicken");
		setTitle("Chicken");
		setSize(400, 340);
		setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - getWidth() / 2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - getHeight() / 2);
		setResizable(false);
		setDefaultCloseOperation(3);
		setLayout(new FlowLayout());
		JPanel ptop = new JPanel();
		JPanel pto = new JPanel();
		JPanel pconverttop = new JPanel();
		JPanel pconvertmid = new JPanel();
		JPanel pconvertbot = new JPanel();
		this.input = new JTextArea("", 6, 30);
		this.output = new JTextArea("", 6, 30);
		output.setEditable(false);
		add(ptop);
		add(pto);
		add(pconverttop);
		add(pconvertmid);
		add(pconvertbot);
		pconverttop.add(this.input);
		pconverttop.add(new JScrollPane(this.input));
		pconvertmid.add(this.totext = new JButton("Unchicken"));
		pconvertmid.add(this.toenc = new JButton("Chicken!"));
		pconvertbot.add(this.output);
		pconvertbot.add(new JScrollPane(this.output));
		this.totext.addActionListener(this);
		this.toenc.addActionListener(this);
		setVisible(true);
	}
	
	public String autoEncrypt(String s) {
		if (isChicken(s))
			return unchicken(s);
		return chicken(s);
	}
	
	public String chicken(String s) {
		String e = "";
		int i = 0;
		for(char c : s.toCharArray()) {
			if (c == ' ')
				e += ",";
			else {
				e += " ";
				boolean first = true;
				String binary = Integer.toBinaryString(c);
				for(char b : binary.toCharArray()) {
					// lower-case = 1 just because it's easier to read
					if (b == '0')
						e += CHICKEN[i];
					else
						e += chicken[i];
					i = (i + 1) % 7;
					if (i == 0 && rand.nextInt(10) == 0)
						e += rand.nextBoolean() ? "!" : "?";
					if (first)
						first = false;
				}
			}
		}
		return e.substring(1);
	}
	
	public String unchicken(String s) {
		String e = "";
		for(String w : s.split(" ")) {
			s = s.replace("?", "").replace("!", "");
			if(w.isEmpty())
				continue;
			if(w.trim().isEmpty()) {
				e += " ";
				continue;
			}
			String binary = "";
			boolean endWord = false;
			for(char c : w.toCharArray()) {
				if(c == ',')
					endWord = true;
				if(!Character.isAlphabetic(c))
					continue;
				if(Character.isUpperCase(c))
					binary += "0";
				else
					binary += "1";
			}
			e += ((char) Integer.parseInt(binary, 2));
			if(endWord)
				e += " ";
		}
		return e;
	}
	
	public boolean isChicken(String s) {
		return s.toLowerCase().replace("chicken", "").replace(".", "").replace("!", "").replace("?", "").replace(",", "").trim().isEmpty();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e == null)
			return;
		Object o = e.getSource();
		if ((o == this.totext) && (this.input.getText().length() > 0))
			this.output.setText(unchicken(this.input.getText()));
		else if ((o == this.toenc) && (this.input.getText().length() > 0))
			this.output.setText(chicken(this.input.getText()));
	}
}