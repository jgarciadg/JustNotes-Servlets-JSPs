package es.justo.giiis.pi.model;

public class ProfileImage {
	private int idi;
	private String url;

	public int getIdi() {
		return idi;
	}

	public void setIdi(int idi) {
		this.idi = idi;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "ProfileImage [idi=" + idi + ", url=" + url + "]";
	}
}
