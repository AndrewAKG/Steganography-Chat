package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

@SuppressWarnings("serial")
public class Gui extends JFrame implements ActionListener, Runnable {

	static Client client;
	static JButton Join;
	static JTextArea Name;
	static JLabel Enter;

	static JLabel signUp;
	static JLabel userNameSignUp;
	static JLabel Confirm;
	static JTextArea userNameSignUpText;
	static JLabel passwordSignUp;
	static JPasswordField passwordSignUpText;
	static JButton signUpButton;
	static JLabel signUpResult;

	static JLabel logIn;
	static JLabel userNameLogIn;
	static JTextArea userNameLogInText;
	static JLabel passwordLogIn;
	static JPasswordField passwordLogInText;
	static JButton logInButton;
	static JLabel logInResult;

	static JButton Get[];
	static JButton Member[];
	static JButton Recent[];
	static JButton Next;
	static JButton Back;
	static JButton NextR;
	static JButton BackR;
	static JLabel Number;
	static JLabel Connect;
	static JLabel User;
	static int number;
	static int current;
	static int currentR;
	static ArrayList<String> onlines;
	static JTextPane Chat;
	static JScrollPane Panel;
	static JTextArea Message;
	static JButton Send;
	static JLabel Contact;
	static ArrayList<Data> data;
	static JLabel Con;
	static JLabel Rec;
	static JButton groupChat;

	static boolean world;

	public Gui() throws Exception {

		onlines = new ArrayList<String>();
		data = new ArrayList<Data>();
		current = 0;
		currentR = 0;

		Font f1 = new Font("Times new Roman", Font.BOLD, 13);
		Font f2 = new Font("Times new Roman", Font.BOLD, 20);
		Font f3 = new Font("Times new Roman", Font.BOLD, 25);

		String HN = InetAddress.getLocalHost().getHostName();
		// HN="192.168.1.3"; //write here the ip adress of the server pc
		client = new Client(HN, 6004);

		signUp = new JLabel("Sign Up");
		signUp.setBounds(40, 130, 200, 40);
		signUp.setFont(f3);
		add(signUp);

		userNameSignUp = new JLabel("Username");
		userNameSignUp.setBounds(40, 190, 160, 40);
		userNameSignUp.setFont(f2);
		add(userNameSignUp);

		passwordSignUp = new JLabel("Password");
		passwordSignUp.setBounds(40, 240, 160, 40);
		passwordSignUp.setFont(f2);
		add(passwordSignUp);

		userNameSignUpText = new JTextArea();
		userNameSignUpText.setBounds(200, 190, 200, 40);
		userNameSignUpText.setFont(f2);
		add(userNameSignUpText);

		passwordSignUpText = new JPasswordField();
		passwordSignUpText.setBounds(200, 240, 200, 40);
		passwordSignUpText.setFont(f2);
		passwordSignUpText.setEchoChar('*');
		add(passwordSignUpText);

		signUpButton = new JButton("Sign Up");
		signUpButton.setBounds(40, 330, 150, 40);
		signUpButton.addActionListener(this);
		signUpButton.setBorderPainted(false);
		signUpButton.setBackground(Color.BLACK);
		signUpButton.setForeground(Color.white);
		add(signUpButton);

		signUpResult = new JLabel("");
		signUpResult.setBounds(40, 450, 250, 40);
		signUpResult.setFont(f2);
		add(signUpResult);

		logIn = new JLabel("Log In");
		logIn.setBounds(640, 130, 200, 40);
		logIn.setFont(f3);
		add(logIn);

		userNameLogIn = new JLabel("Username");
		userNameLogIn.setBounds(640, 190, 160, 40);
		userNameLogIn.setFont(f2);
		add(userNameLogIn);

		passwordLogIn = new JLabel("Password");
		passwordLogIn.setBounds(640, 240, 160, 40);
		passwordLogIn.setFont(f2);
		add(passwordLogIn);

		userNameLogInText = new JTextArea();
		userNameLogInText.setBounds(800, 190, 200, 40);
		userNameLogInText.setFont(f2);
		add(userNameLogInText);

		passwordLogInText = new JPasswordField();
		passwordLogInText.setBounds(800, 240, 200, 40);
		passwordLogInText.setFont(f2);
		passwordLogInText.setEchoChar('*');
		add(passwordLogInText);

		logInButton = new JButton("Log In");
		logInButton.setBounds(640, 330, 150, 40);
		logInButton.addActionListener(this);
		logInButton.setBorderPainted(false);
		logInButton.setBackground(Color.BLACK);
		logInButton.setForeground(Color.white);
		add(logInButton);

		logInResult = new JLabel("");
		logInResult.setBounds(640, 450, 350, 40);
		logInResult.setFont(f2);
		add(logInResult);

		Get = new JButton[5];
		for (int i = 0; i < 1; i++) {
			Get[i] = new JButton("Server " + i);
			Get[i].setActionCommand("g" + i);
			Get[i].setBounds(300 + (i * 100), 50, 100, 50);
			Get[i].setVisible(false);
			Get[i].addActionListener(this);
			Get[i].setBackground(Color.MAGENTA);
			Get[i].setFont(f1);
			add(Get[i]);
		}
		Get[0].setText("ALL Users");
		Get[0].setBackground(Color.BLUE);

		groupChat = new JButton("World Chat");
		groupChat.setActionCommand("w");
		groupChat.setBounds(400, 50, 100, 50);
		groupChat.setVisible(false);
		groupChat.addActionListener(this);
		groupChat.setBackground(Color.cyan);
		groupChat.setFont(f1);
		add(groupChat);

		Member = new JButton[8];
		for (int i = 0; i < 8; i++) {
			Member[i] = new JButton();
			Member[i].setBounds(75, 50 + 50 * i, 100, 50);
			Member[i].setVisible(false);
			Member[i].setActionCommand("b");
			Member[i].addActionListener(this);
			add(Member[i]);
		}

		Con = new JLabel("Server Users");
		Con.setBounds(70, 10, 150, 35);
		Con.setFont(f2);
		Con.setVisible(false);
		add(Con);

		Recent = new JButton[5];
		for (int i = 0; i < 5; i++) {
			Recent[i] = new JButton();
			Recent[i].setBounds(890, 180 + 50 * i, 100, 50);
			Recent[i].setVisible(false);
			Recent[i].setActionCommand("r");
			Recent[i].addActionListener(this);
			Recent[i].setBackground(Color.cyan);
			add(Recent[i]);
		}

		Rec = new JLabel("Recent Chats");
		Rec.setBounds(890, 130, 130, 40);
		Rec.setVisible(false);
		Rec.setFont(f2);
		add(Rec);

		ImageIcon Inext = resize("next.png", 100, 50);
		ImageIcon Iback = resize("back.png", 100, 50);

		Next = new JButton("next");
		Next.setBounds(150, 500, 100, 50);
		Next.setIcon(Inext);
		Next.setContentAreaFilled(false);
		Next.addActionListener(this);
		Next.setVisible(false);
		Next.setBorderPainted(false);
		add(Next);

		Back = new JButton("back");
		Back.setBounds(25, 500, 100, 50);
		Back.setIcon(Iback);
		Back.setContentAreaFilled(false);
		Back.addActionListener(this);
		Back.setVisible(false);
		Back.setBorderPainted(false);
		add(Back);

		NextR = new JButton("next");
		NextR.setBounds(985, 500, 100, 50);
		NextR.setIcon(Inext);
		NextR.setContentAreaFilled(false);
		NextR.addActionListener(this);
		NextR.setActionCommand("NR");
		NextR.setVisible(false);
		NextR.setBorderPainted(false);
		add(NextR);

		BackR = new JButton("back");
		BackR.setBounds(860, 500, 100, 50);
		BackR.setIcon(Iback);
		BackR.setContentAreaFilled(false);
		BackR.addActionListener(this);
		BackR.setActionCommand("BR");
		BackR.setVisible(false);
		BackR.setBorderPainted(false);
		add(BackR);

		Number = new JLabel();
		Number.setBounds(300, 500, 400, 50);
		Number.setVisible(false);
		Number.setFont(f2);
		add(Number);

		Connect = new JLabel("Disconnected");
		Connect.setBounds(900, 10, 150, 50);
		Connect.setForeground(Color.red);
		Connect.setFont(f2);
		add(Connect);

		User = new JLabel("");
		User.setBounds(900, 70, 170, 50);
		User.setFont(f2);
		add(User);

		Chat = new JTextPane();
		Chat.setEditable(false);
		Chat.setFont(f2);

		Panel = new JScrollPane(Chat);
		Panel.setBounds(250, 150, 600, 300);
		Panel.setVisible(false);
		add(Panel);

		Message = new JTextArea();
		Message.setBounds(250, 460, 480, 50);
		Message.setVisible(false);
		Message.setFont(f2);
		add(Message);

		ImageIcon Isend = resize("send.png", 100, 50);
		Send = new JButton("send");
		Send.setBounds(750, 460, 100, 50);
		Send.setVisible(false);
		Send.addActionListener(this);
		Send.setIcon(Isend);
		Send.setContentAreaFilled(false);
		Send.setBorderPainted(false);
		add(Send);

		Contact = new JLabel();
		Contact.setBounds(250, 110, 500, 40);
		Contact.setVisible(false);
		Contact.setFont(f2);
		add(Contact);

		setSize(1100, 600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);

		getContentPane().setBackground(Color.lightGray);
		setVisible(true);

		world = false;

		new Thread(new Gui(client)).start();
	}

	public Gui(Client c) {
		client = c;
	}

	public static ImageIcon resize(String s, int width, int height) {

		BufferedImage image = null;

		try {
			image = ImageIO.read(new File(s));
		} catch (IOException e) {
		}

		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
		Graphics2D g2d = (Graphics2D) bi.createGraphics();
		g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
		g2d.drawImage(image, 0, 0, width, height, null);
		g2d.dispose();
		return new ImageIcon(bi);
	}

	public static void main(String args[]) throws Exception {
		new Gui();
	}

	public static String encrypt(String key, String initVector, String value) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(value.getBytes());

			Encoder encoder = Base64.getEncoder();
			String encodedString = encoder.encodeToString(encrypted);

			System.out.println("encrypted string: " + encodedString);

			return encodedString;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public static String decrypt(String key, String initVector, String encrypted) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

			Decoder decoder = Base64.getDecoder();
			byte[] decodedString = decoder.decode(encrypted);

			byte[] original = cipher.doFinal(decodedString);

			return new String(original);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public void join(String userName) {
		String ftxt = userName;
		String newtxt = "";
		String txt = "";
		boolean start = false;
		boolean end = true;

		for (int i = ftxt.length() - 1; i > -1; i--) {
			if (ftxt.charAt(i) == ' ' && end)
				;
			else {
				end = false;
				txt = ftxt.charAt(i) + txt;
			}

		}

		for (int i = 0; i < txt.length(); i++) {
			if (txt.charAt(i) == ' ') {
				if (start)
					newtxt = newtxt + '_';
			} else {
				newtxt = newtxt + txt.charAt(i);
				start = true;
			}
		}

		client.Join(newtxt);
	}

	public static String executePost(String targetURL, String urlParameters) {
		HttpURLConnection connection = null;

		try {
			// Create connection
			URL url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");

			connection.setUseCaches(false);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.close();

			int responseCode = connection.getResponseCode();
			System.out.println("POST Response Code :: " + responseCode);

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder(); // or StringBuffer if
															// Java version 5+
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			System.out.println("response: " + response.toString());
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	public static File chooseRandomImg() {
		int x = (int) Math.random() * 7 + 1;
		return new File(x + ".png");
	}

	public static String encodeImage(byte[] imageBytes) {
		return Base64.getEncoder().encodeToString(imageBytes);
	}

	public static BufferedImage encrypt(BufferedImage input, String s) {
		s += "E!N@D";
		char[] message = s.toCharArray();
		int messageIndex = 0;

		for (int i = 0; i < input.getHeight(); ++i) {
			for (int j = 0; j < input.getWidth(); ++j) {
				if (messageIndex + 1 >= message.length * 8)
					return input;

				int b1 = (message[(messageIndex / 8)] >> messageIndex % 8) & 1,
						b2 = ((message[(messageIndex / 8)] >> (messageIndex % 8 + 1)) & 1) << 1, c = b1 | b2;

				messageIndex += 2;

				int x = input.getRGB(j, i);
				x = x & ~(1 << 0);
				x = x & ~(1 << 1);
				x = x | c;
				input.setRGB(j, i, x);
			}
		}

		return input;
	}

	public String ImageEncryption(String message, File imageFile) {
		try {
			BufferedImage image = ImageIO.read(imageFile);
			BufferedImage encryptedImg = encrypt(image, message);

			File output = new File("out.png");
			ImageIO.write(encryptedImg, "png", output);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(encryptedImg, "png", baos);
			byte[] imageInByte = baos.toByteArray();
			baos.close();

			String imageDataString = encodeImage(imageInByte);

			// send data
			return imageDataString;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public static byte[] decodeImage(String imageDataString) {
		return Base64.getDecoder().decode(imageDataString);
	}

	public static String decrypt(BufferedImage input) {
		ArrayList<Integer> bits = new ArrayList<Integer>();

		for (int i = 0; i < input.getHeight(); ++i) {
			for (int j = 0; j < input.getWidth(); ++j) {
				bits.add(input.getRGB(j, i) & 1);
				bits.add((input.getRGB(j, i) >> 1) & 1);
			}
		}

		String output = "";
		char c = 0b0;
		for (int i = 0; i < bits.size(); ++i) {
			c |= (bits.get(0) << (i % 8));
			bits.remove(0);
			if (i % 8 == 7) {
				output += c;
				c = 0b0;
			}
		}
		return output.substring(0, output.indexOf("E!N@D"));
	}

	public static String handleDecryption(String input) {
		try {
			byte[] imageByteArray = decodeImage(input);

			ByteArrayInputStream bais = new ByteArrayInputStream(imageByteArray);
			BufferedImage imgToDec = ImageIO.read(bais);

			String message = decrypt(imgToDec);

			return message;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Error";
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String s = e.getActionCommand();
		String t = ((JButton) e.getSource()).getText();

		if (s.equals("Sign Up")) {
			String userName = userNameSignUpText.getText();
			String password = passwordSignUpText.getText();
			String targetURL = "http://localhost:8500/";
			String urlParameters;
			urlParameters = "username" + userName + "&password" + password + "&signup";
			String respond = executePost(targetURL, urlParameters);
			System.out.println("respond in last step: " + respond);
			userNameSignUpText.setText("");
			passwordSignUpText.setText("");

			if (respond.contains("User Signed Up Successfully")) {
				signUpResult.setText(respond);
			} else {
				signUpResult.setText("username already exists");
			}
		}

		if (s.equals("Log In")) {
			String userName = userNameLogInText.getText();
			String password = passwordLogInText.getText();
			String targetURL = "http://localhost:8500/";
			String urlParameters;
			urlParameters = "username" + userName + "&password" + password + "&login";
			String respond = executePost(targetURL, urlParameters);
			if (respond.contains("LogIn successfully")) {
				join(userName);
			}
		}

		if (s.equals("join")) {
			String ftxt = Name.getText();
			String newtxt = "";
			String txt = "";
			boolean start = false;
			boolean end = true;

			for (int i = ftxt.length() - 1; i > -1; i--) {
				if (ftxt.charAt(i) == ' ' && end)
					;
				else {
					end = false;
					txt = ftxt.charAt(i) + txt;
				}

			}

			for (int i = 0; i < txt.length(); i++) {
				if (txt.charAt(i) == ' ') {
					if (start)
						newtxt = newtxt + '_';
				} else {
					newtxt = newtxt + txt.charAt(i);
					start = true;
				}
			}

			client.Join(newtxt);
		}

		if (s.charAt(0) == 'g') {

			Con.setVisible(true);

			int x = Integer.parseInt(s.substring(1));
			if (x == 0)
				x = 5;
			client.GetMemberList(x);
			current = 0;
		}
		if (s.equals("next")) {
			Next.setVisible(false);
			current = current + 8;
			for (int i = 0; i < 8; i++)
				Member[i].setVisible(false);
			for (int i = current; i < current + 8 && i < onlines.size(); i++) {
				int x = i - current;
				Member[x].setText(onlines.get(i));
				Member[x].setVisible(true);
			}
			Back.setVisible(true);
			if (onlines.size() > current + 8)
				Next.setVisible(true);
		}
		if (s.equals("back")) {
			Back.setVisible(false);
			current = current - 8;

			for (int i = 0; i < 8; i++)
				Member[i].setVisible(true);

			for (int i = current; i < current + 8; i++) {
				int x = i - current;
				Member[x].setText(onlines.get(i));
			}
			Next.setVisible(true);

			if (current != 0)
				Back.setVisible(true);
		}
		if (s.equals("NR")) {
			NextR.setVisible(false);
			currentR = currentR + 5;
			for (int i = 0; i < 5; i++)
				Recent[i].setVisible(false);
			for (int i = currentR; i < currentR + 5 && i < data.size(); i++) {
				int x = i - currentR;
				Recent[x].setText(data.get(i).getName());
				Recent[x].setVisible(true);

				if (data.get(i).isRead())
					Recent[x].setBackground(Color.cyan);
				else
					Recent[x].setBackground(Color.green);

			}
			BackR.setVisible(true);
			if (data.size() > currentR + 5)
				NextR.setVisible(true);
		}
		if (s.equals("BR")) {
			BackR.setVisible(false);
			currentR = currentR - 5;

			for (int i = 0; i < 5; i++)
				Recent[i].setVisible(true);

			for (int i = currentR; i < currentR + 5; i++) {
				int x = i - currentR;
				Recent[x].setText(data.get(i).getName());

				if (data.get(i).isRead())
					Recent[x].setBackground(Color.cyan);
				else
					Recent[x].setBackground(Color.green);
			}
			NextR.setVisible(true);

			if (currentR != 0)
				BackR.setVisible(true);
		}

		if (s.equals("r") || s.equals("b") || s.equals("w")) {

			Chat.setText("");
			Message.setText("");
			Contact.setText(t);
			int y = 0;

			for (int i = 0; i < data.size(); i++) {
				String x = data.get(i).getName();
				boolean grp = data.get(i).isGroup();

				if (x.equals(t)) {
					Chat.setText(data.get(i).getMessage());
					data.get(i).setRead(true);
					y = i % 5;
					break;
				}
				if (grp && s.equals("w")) {

					Chat.setText(data.get(i).getMessage());
					data.get(i).setRead(true);
					groupChat.setBackground(Color.CYAN);
					break;
				}
			}

			Panel.setVisible(true);
			Message.setVisible(true);
			Send.setVisible(true);
			Contact.setVisible(true);

			if (s.equals("r")) {
				Recent[y].setBackground(Color.cyan);
				world = false;
			}
			if (s.equals("b")) {

				for (int i = 0; i < 5; i++) {

					if (t.equals(Recent[i].getText())) {
						Recent[i].setBackground(Color.cyan);
					}
				}
				world = false;

			}

			if (s.equals("w")) {
				world = true;
			}
		}

		if (s.equals("send")) {
			String message = Message.getText();
			if (message.length() > 2500) {
				String q = Chat.getText();
				Chat.setText(q + "your message is longer than the allowed capacity");
				new java.util.Timer().schedule(new java.util.TimerTask() {
					@Override
					public void run() {
						Chat.setText(q);
					}
				}, 3000);
			} else {
				String encryptedMsg = ImageEncryption(message, chooseRandomImg());
				String q = Chat.getText();

				q = q + "Me: " + message + "\n";
				if (world)
					client.groupChat(client.UserName, 2, encryptedMsg);
				else
					client.Chat(client.UserName, Contact.getText(), 2, encryptedMsg);

				String z = Chat.getText();

				if (z.equals("")) {
					Data d = null;

					if (world)
						d = new Data(Contact.getText(), q, true, true);
					else
						d = new Data(Contact.getText(), q, true, false);

					setFirst(null, d);
					groupChat.setBackground(Color.CYAN);
				} else {
					for (int i = 0; i < data.size(); i++) {
						String x = data.get(i).getName();
						boolean grp = data.get(i).isGroup();

						if (world && grp) {
							data.get(i).setMessage(q);
							setFirst(data.get(i), data.get(i));
							groupChat.setBackground(Color.CYAN);
							break;
						}
						if (x.equals(Contact.getText())) {
							data.get(i).setMessage(q);
							setFirst(data.get(i), data.get(i));
							break;
						}
					}
				}

				Message.setText("");
				Chat.setText(q);
			}
		}
	}

	@Override
	public void run() {

		try {

			while (true) {
				String line = client.b.readLine();
				evaluate(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void evaluate(String line) {

		String words[] = line.split(" ");

		if (line.equals("connected")) {
			signUp.setVisible(false);
			userNameSignUp.setVisible(false);
			userNameSignUpText.setVisible(false);
			passwordSignUp.setVisible(false);
			passwordSignUpText.setVisible(false);
			signUpButton.setVisible(false);
			signUpResult.setVisible(false);

			logIn.setVisible(false);
			userNameLogIn.setVisible(false);
			userNameLogInText.setVisible(false);
			passwordLogIn.setVisible(false);
			passwordLogInText.setVisible(false);
			logInButton.setVisible(false);
			logInResult.setVisible(false);

			for (int i = 0; i < 1; i++)
				Get[i].setVisible(true);

			groupChat.setVisible(true);

			Connect.setText("Connected");
			Connect.setForeground(Color.green);
			User.setText(client.UserName);
			Rec.setVisible(true);
		}
		if (words[0].equals("usernames")) {
			number = (words.length) - 1;
			onlines = new ArrayList<String>();

			if (number == -1)
				number = 0;

			for (int i = 1; i < words.length; i++) {
				onlines.add(words[i]);
			}

			for (int i = 0; i < 8; i++)
				Member[i].setVisible(false);

			for (int i = 0; i < 8 && i < onlines.size(); i++) {
				Member[i].setText(onlines.get(i));
				Member[i].setVisible(true);
			}
			if (onlines.size() > 8)
				Next.setVisible(true);

			Number.setText(number + " User(s) Available");
			Number.setVisible(true);
		}

		if (words[0].contains("from")) {

			String msg = line.substring(3 + words[0].length() + words[1].length());

			String decryptedMsg = handleDecryption(msg);
			Data s = null;
			Data f = null;
			boolean here = words[1].equals(Contact.getText());
			boolean found = false;

			if (words[1].equals("server")) {
				String q = (Chat.getText() + words[1] + ":" + " Previous Message not Received, " + Contact.getText()
						+ " is Disconnected" + "\n");
				Chat.setText(q);

				for (int i = 0; i < data.size(); i++) {
					String p = data.get(i).getName();
					if (p.equals(Contact.getText())) {
						data.get(i).setMessage(q);
						break;
					}

				}

				return;
			}

			for (int i = 0; i < data.size(); i++) {
				String x = data.get(i).getName();
				boolean grp = data.get(i).isGroup();

				if (grp && words[0].equals("fromA")) {

					Data d = data.get(i);
					String q = d.getMessage();
					String z = q + words[1] + ":" + decryptedMsg + "\n";
					f = new Data(words[1], z, false, true);

					if (world) {
						f.setRead(true);
						Chat.setText(f.getMessage());
						groupChat.setBackground(Color.CYAN);
					}

					setFirst(d, f);
					found = true;
					break;
				}

				if (x.equals(words[1])) {
					Data d = data.get(i);
					String q = d.getMessage();
					String z = q + words[1] + ":" + decryptedMsg + "\n";
					f = new Data(words[1], z, false, false);

					if (here) {
						f.setRead(true);
						Chat.setText(f.getMessage());
					}

					setFirst(d, f);
					found = true;
					break;
				}
			}

			if (!found) {

				if (words[0].equals("fromA"))
					s = new Data("world", words[1] + ":" + decryptedMsg + "\n", false, true);
				else
					s = new Data(words[1], words[1] + ":" + decryptedMsg + "\n", false, false);

				if (here) {
					s.setRead(true);
					Chat.setText(s.getMessage());
				}

				setFirst(null, s);

			}

		}

	}

	public static void setFirst(Data d, Data f) {

		if (f.isGroup()) {

			groupChat.setBackground(Color.GREEN);
			data.remove(d);
			data.add(0, f);
			return;
		}

		data.remove(d);
		data.add(0, f);
		NextR.setVisible(false);
		BackR.setVisible(false);

		for (int i = 0; i < 5; i++)
			Recent[i].setVisible(false);

		ArrayList<Data> newData = new ArrayList<Data>();

		for (int i = 0; i < data.size(); i++) {
			Data d2 = data.get(i);
			if (!d2.isGroup())
				newData.add(d2);
		}

		for (int i = currentR; i < currentR + 5 && i < newData.size(); i++) {

			int k = i - currentR;

			if (!newData.get(i).isRead())
				Recent[k].setBackground(Color.GREEN);
			else
				Recent[k].setBackground(Color.cyan);

			Recent[k].setText(newData.get(k).getName());
			Recent[k].setVisible(true);

		}

		if (currentR != 0)
			BackR.setVisible(true);

		if (data.size() > currentR + 5)
			NextR.setVisible(true);

	}

}

class Data {

	private String name;
	private String message;
	private boolean read;
	private boolean group;

	public Data(String name, String message, boolean read, boolean group) {
		super();
		this.name = name;
		this.message = message;
		this.read = read;
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public boolean isGroup() {
		return group;
	}

	public void setGroup(boolean group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return "Data [name=" + name + ", message=" + message + ", read=" + read + ", group=" + group + "]";
	}

}
