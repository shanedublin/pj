package pj;

import java.util.ArrayList;

public class HighScores {
	ArrayList<Score> scores;
	
	public	HighScores(){
	this.scores = new ArrayList();	
	}
	
	public void addScore(Score score){
		this.scores.add(score);
	}
	
	public void removeScore(int i){
		scores.remove(i);
	}
	
	public void trimScores(){
		if (scores.size()> 10){
			scores.remove(scores.size()-1);
			trimScores();
		}
		
	}
	
	
	//TODO order the scores in ascending order
	public void sortScores(int n){
		int high = scores.get(n).score;
		
		for(int i = n+1; i < scores.size(); i ++){
			if(scores.get(i).score > high){
				high = scores.get(i).score;
				swap(n,i);
			}
		}
		if(n+1 < scores.size()){
			sortScores(n+1);
			
		}
		
		
		
	}

	// wich two to swap
	public void swap(int n, int i){
		Score temp;
		temp = scores.get(n);
		scores.set(n, scores.get(i));
		scores.set(i, temp);
		
	}
}
