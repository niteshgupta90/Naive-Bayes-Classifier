package NaiveBayes;
public class WordClass {
	
	private int word_Index;
	private int frequency;
	
	public WordClass(int word_Index, int frequency)
	{
		this.word_Index = word_Index;
		this.frequency = frequency;
	}
	public void setWord_Index(int wordIdx) {
		this.word_Index = wordIdx;
	}
	public int getWord_Index() {
		return word_Index;
	}
	public void setCount(int count) {
		this.frequency = count;
	}
	public int getCount() {
		return frequency;
	}
}
