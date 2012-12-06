package com.baoxue.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;

import com.baoxue.annotation.CheckLogin;
import com.baoxue.common.ActionBase;
import com.baoxue.common.Helper;
import com.baoxue.common.HibernateSessionFactory;
import com.baoxue.db.TUsers;

@CheckLogin(check = false)
public class Login extends ActionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2249408819631153958L;
	private String userName;
	private String password;
	private String imgCode;
	private String msg;
	// private File file;
	// private String fileContentType;
	// private String fileFileName;
	private static final char[] IMG_CODE_CHAR = new char[] { 'A', 'B', 'C',
			'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R',
			'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6',
			'7', '8', '9' };

	// public String getFileFileName() {
	// return fileFileName;
	// }
	//
	// public void setFileFileName(String fileFileName) {
	// this.fileFileName = fileFileName;
	// }
	//
	// public File getFile() {
	// return file;
	// }
	//
	// public void setFile(File file) {
	// this.file = file;
	// }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getImgCode() {
		return imgCode;
	}

	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}

	public String execute() {
		int LOGIN_TYPE = getSession().getAttribute("LOGIN_TYPE") == null ? 0
				: (Integer) getSession().getAttribute("LOGIN_TYPE");
		if (LOGIN_TYPE == 1) {
			return SUCCESS;
		}
		if (isPost()) {

			String save_img_code = (String) getSession().getAttribute(
					"login_img_code");
			getRequest().getSession().setAttribute("login_img_code", null);
			if (save_img_code == null
					|| !save_img_code.toLowerCase().equals(
							getImgCode().toLowerCase())) {
				setMsg(Helper.getString("img_code_error"));
				return LOGIN;
			}

			Session session = HibernateSessionFactory.getSession();
			String hql = "from TUsers u where u.username=:user and u.password=:password";
			try {

				Query query = session.createQuery(hql);
				query.setString("user", getUserName());
				System.out.println(getPassword());
				String md5 = Helper.getMD5(getPassword());
				System.out.println(getPassword()+":md5:" + md5);
				query.setString("password", md5);
				@SuppressWarnings("rawtypes")
				List res = query.list();
				if (res.size() > 0) {
					TUsers user = (TUsers) res.get(0);
					if (user.getAdminid() == 1) {
						getSession().setAttribute("LOGIN_TYPE", 1);
						return SUCCESS;
					} else {
						setMsg(Helper.getString("not_admin"));
						return LOGIN;
					}

				}

			} catch (Throwable ex) {
				ex.printStackTrace();
			} finally {
				session.close();
			}
			setUserName("");
			setPassword("");

			setMsg(Helper.getString("login_faile"));

			// try {
			// FileInputStream input = new FileInputStream(f1);
			//
			// } catch (FileNotFoundException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

		}
		return LOGIN;
	}

	public String imgCode() {
		return SUCCESS;
	}

	public InputStream getRandImg() {
		try {
			PipedInputStream input = new PipedInputStream();
			final PipedOutputStream out = new PipedOutputStream(input);

			int width = 90;
			int height = 25;
			final BufferedImage buffImg = new BufferedImage(width, height,
					BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g = buffImg.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
					RenderingHints.VALUE_STROKE_DEFAULT);
			Random random = new Random(); // 生成一个随机数
			// g.setColor(Color.BLACK); // 设置背景颜色
			// g.fillRect(0, 0, width, height); // 填充一个矩形

			Font font = new Font(null, Font.PLAIN, 25);
			g.setFont(font); // 设置字体

			g.setColor(Color.WHITE);
			g.fillRect(0, 0, width - 1, height - 1); // 绘制边框

			int red = 0, green = 0, blue = 0;
			// 随机产生100条干扰线，使图像中的认证码不易被其它程序探测到
			for (int i = 0; i < 100; i++) {
				red = random.nextInt(100);
				green = random.nextInt(100);
				blue = random.nextInt(100);

				// 用随机产生的颜色将验证码绘制到图像中
				g.setColor(new Color(red + 100, green + 100, blue + 100));
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				int x1 = random.nextInt(20);
				int y1 = random.nextInt(20);
				g.drawLine(x, y, x + x1, y + y1);
			}

			StringBuffer randomCode = new StringBuffer(); // 用于保存随机产生的验证码

			// 随机产生四位数的验证码
			for (int i = 0; i < 4; i++) {
				// 得到单个验证码
				String strRand = String.valueOf(IMG_CODE_CHAR[random
						.nextInt(IMG_CODE_CHAR.length)]);

				// 随机生成颜色值，使生成的字符验证码颜色各不相同
				red = random.nextInt(100);
				green = random.nextInt(100);
				blue = random.nextInt(50);

				int dev = random.nextInt(10);

				// 用随机产生的颜色将验证码绘制到图像中
				g.setColor(new Color(red, green, blue));

				g.drawString(strRand, 20 * i + dev, 20 + random.nextInt(2));

				// 将产生的四个随机字符组合在一起
				randomCode.append(strRand);
			}

			// 将四位数字的验证码保存到Session里面
			HttpSession session = getSession();
			session.setAttribute("login_img_code", randomCode.toString());
			System.out.println("login_img_code:" + randomCode.toString());
			HttpServletResponse resp = getResponse();
			// 禁止图像缓存
			resp.setHeader("Prama", "no-cache");
			resp.setHeader("Coche-Control", "no-cache");
			resp.setDateHeader("Expires", 0);

			new Thread() {

				@Override
				public void run() {
					try {
						ImageIO.write(buffImg, "png", out);

						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}.start();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return input;
		} catch (IOException e) {

			e.printStackTrace();
		}

		return null;

	}

	public String logout() {
		getSession().removeAttribute("LOGIN_TYPE");
		return LOGIN;
	}
}
