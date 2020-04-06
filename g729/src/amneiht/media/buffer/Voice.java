package amneiht.media.buffer;

public abstract class Voice implements Runnable{
	
	/**
	 * them 1 ban ghi am thanh vao play list
	 * @param con
	 * @param id
	 */
	public abstract void addList(byte[] con,long id);
	/**
	 * phat doan am thanh do
	 */
	public  abstract void play();
	/**
	 * lay doan id do
	 * @return
	 */
	public  abstract long getId() ;
	/**
	 * ket thuc chuong luong
	 */
	public  abstract void end();
}
