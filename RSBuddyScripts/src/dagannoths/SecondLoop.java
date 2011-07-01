package dagannoths;

import com.rsbuddy.script.task.LoopTask;


public class SecondLoop extends LoopTask {

	
	@Override
	public int loop() {
		
		for (Skill s: MavenDagannoths.skills){
			 if (s.xpGained() > 0 && !MavenDagannoths.addSkills.contains(s))
				 MavenDagannoths.addSkills.add(s);
		 }
		return 100;
	}

}
