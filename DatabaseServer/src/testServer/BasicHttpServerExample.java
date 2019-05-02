package testServer;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class BasicHttpServerExample {

	public static void main(String[] args) throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(8500), 0);
		HttpContext context = server.createContext("/");
		context.setHandler(BasicHttpServerExample::handleRequest);
		server.start();
	}

	private static void handleRequest(HttpExchange exchange) throws IOException {
		System.out.println("the request has arrived");
		System.out.println("-- Paramter --");
		System.out.println(exchange.getRequestBody());
		InputStream exc = exchange.getRequestBody();
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;

		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(exc))) {
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
		}
		System.out.println(stringBuilder.toString());
		String response;

		String[] parameters = stringBuilder.toString().split("&");
		String username = parameters[0].substring(8, parameters[0].length());
		String password = parameters[1].substring(8, parameters[1].length());
		if (parameters[2].equals("signup")) {
			response = addToFile(username, password);
		} else {
			response = checkAuthentication(username, password);
		}

		exchange.sendResponseHeaders(200, response.getBytes().length);// response
																		// code
																		// and
																		// length
		OutputStream os = exchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}

	public static String encrypt(String key, String initVector, String value) {
		try {
			IvParameterSpec iv = new IvParameterSpec(
					initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"),
					"AES");

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
			IvParameterSpec iv = new IvParameterSpec(
					initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"),
					"AES");

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

	public static String checkAuthentication(String userName, String password) {

		try {
			boolean flag = false;
			String currentLine = "";
			FileReader reader = new FileReader("data.csv");
			BufferedReader br = new BufferedReader(reader);
			String key = "Bar12345Bar12345"; // 128 bit key
			String initVector = "RandomInitVect11"; // 16 bytes IV
			while ((currentLine = br.readLine()) != null) {
				String data[] = currentLine.split(",");
				if (data[0].equals(userName)) {
					System.out.println(userName);
					String decryptedPassword = decrypt(key, initVector, data[1]);
					if (decryptedPassword.equals(password)) {
						System.out.println("password not equal");
						flag = true;
						break;
					}
				}
			}

			reader.close();

			if (flag) {
				return ("LogIn successfully");

			} else {
				return ("username or password is not correct!");
			}

		} catch (Exception e) {

		}
		return ("LogIn didn't go successfully");
	}

	@SuppressWarnings("resource")
	public static String addToFile(String userName, String password) {

		boolean found = false;

		try {
			new FileReader("data.csv");
			found = true;
		} catch (Exception exc) {
			found = false;
		}

		if (found) {

			try {

				boolean flag = false;
				String currentLine = "";
				FileReader reader = new FileReader("data.csv");
				BufferedReader br = new BufferedReader(reader);

				while ((currentLine = br.readLine()) != null) {
					String data[] = currentLine.split(",");
					if (data[0].equals(userName)) {
						flag = true;
						return ("Username already exists!");
					}
				}

				reader.close();

				if (!flag) {

					String key = "Bar12345Bar12345"; // 128 bit key

					String initVector = "RandomInitVect11"; // 16 bytes IV
					String encryptedPassword = encrypt(key, initVector,
							password);

					FileWriter csvWriter = new FileWriter("data.csv", true);

					csvWriter.append(userName);
					csvWriter.append(",");
					csvWriter.append(encryptedPassword);
					csvWriter.append("\n");

					csvWriter.flush();
					csvWriter.close();
					return ("User Signed Up Successfully");

				}

			} catch (Exception exc) {
				System.out.println(exc);
			}

		} else {

			try {
				String key = "Bar12345Bar12345"; // 128 bit key
				String initVector = "RandomInitVect11"; // 16 bytes IV
				String encryptedPassword = encrypt(key, initVector, password);

				FileWriter csvWriter = new FileWriter("data.csv");

				csvWriter.append(userName);
				csvWriter.append(",");
				csvWriter.append(encryptedPassword);
				csvWriter.append("\n");

				csvWriter.flush();
				csvWriter.close();

				return ("User Signed Up Successfully");

			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return ("User failed to Signed up");
	}
}