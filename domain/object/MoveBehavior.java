package domain.object;
import domain.Constant;

/**
* MoveBehavior interface
* @author Softwaring 
* @since 02-12-2021
* @version 2.0
*/
public interface MoveBehavior<M> {
	public double width = Constant.width;
	public double height = Constant.height;
	public void move(M obj);
}
