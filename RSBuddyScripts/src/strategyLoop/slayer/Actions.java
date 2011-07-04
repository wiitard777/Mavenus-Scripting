package strategyLoop.slayer;

public interface Actions {

	public boolean isValid();
	
	public void execute();
	
	public String getStatus();
	
	public int getSleep();
	
}