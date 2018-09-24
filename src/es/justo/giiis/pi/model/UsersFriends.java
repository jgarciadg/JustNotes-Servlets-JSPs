package es.justo.giiis.pi.model;

public class UsersFriends {
	private int idu;
	private int idfriend;

	public UsersFriends() {
		super();
	}

	public UsersFriends(int idu, int idfriend) {
		super();
		this.idu = idu;
		this.idfriend = idfriend;
	}

	public int getIdu() {
		return idu;
	}

	public void setIdu(int idu) {
		this.idu = idu;
	}

	public int getIdfriend() {
		return idfriend;
	}

	public void setIdfriend(int idfriend) {
		this.idfriend = idfriend;
	}

	@Override
	public String toString() {
		return "UsersFriends [idu=" + idu + ", idfriend=" + idfriend + "]";
	}

}
