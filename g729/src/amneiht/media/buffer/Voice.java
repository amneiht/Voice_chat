package amneiht.media.buffer;

import java.io.Closeable;

public abstract class Voice implements Runnable ,Closeable{
	/**
	 * them 1 ban ghi am thanh vao play list
	 * @param con
	 * @param id
	 */
	public abstract void addList(Pack con);
	/**
	 * phat doan am thanh do
	 */
	public  abstract void play();
	/**
	 * lay doan id do
	 * @return
	 */
	public  abstract boolean isrun();
}
