package es.justo.giiis.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.justo.giiis.pi.model.ProfileImage;

public class JDBCProfileImageDAOImpl implements ProfileImageDAO {
	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCLabelDAOImpl.class.getName());
	private static final String TAG = "JDBCProfileImageImpl: ";
	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<ProfileImage> getAll() {
		if (conn == null)
			return null;

		ArrayList<ProfileImage> images = new ArrayList<ProfileImage>();
		try {
			Statement stmt;
			ResultSet rs;
			synchronized (conn) {
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT * FROM ProfileImages");
			}
			while (rs.next()) {
				ProfileImage image = new ProfileImage();
				image.setIdi(rs.getInt("idi"));
				image.setUrl(rs.getString("url"));

				images.add(image);
				logger.info(TAG + "fetching profile images: " + image.getIdi() + " " + image.getUrl());

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return images;

	}

	@Override
	public ProfileImage getByIdi(int idi) {
		if (conn == null)
			return null;

		ProfileImage image = new ProfileImage();
		try {
			Statement stmt;
			ResultSet rs;
			synchronized (conn) {
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT * FROM ProfileImages WHERE idi=" + idi);
			}
			while (rs.next()) {
				image.setIdi(rs.getInt("idi"));
				image.setUrl(rs.getString("url"));

				logger.info(TAG + "fetching profile image: " + image.getIdi() + " " + image.getUrl());

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return image;
	}

}
