package domain.magic;
/**
 * Activatable is an interface which is implemented by the magical abilities.
 * @author Softwaring
 */
public interface Activatable {
	public void activate();
	public void deactivate();
	public int getKeyCode();
	public void setKeyCode(int keyCode);
}
