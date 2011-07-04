package strategyLoop.slayer;

public abstract class ExampleAttack implements Actions {
	
	public boolean isValid() {
		//should probably always return true, but have nothing beneath it in the
		//loop of the actions
	return true;
	
	}
	
	public void execute(){
		/*
		 * eating, attacking, praying and potions, all need to be handled in each
		 * attack class, simply because you don't eat food at iron dragons, and 
		 * you don't use prayer at dagannoths.  as such we need to have each
		 * attack method to handle eating, praying etc.
		 * 
		 */
	}
	
	public String getStatus(){
		return "attacking or something rather";
	}
	
	public int getSleep(){
		return 1000;//that or another number that applies to that particular monster
	}

}
