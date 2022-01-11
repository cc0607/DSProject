import java.io.IOException;
import java.util.ArrayList;

import javax.swing.text.StyledEditorKit.ForegroundAction;

public class WebTree {
	
public WebNode root;
	
	public WebTree(WebPage rootPage){
		this.root = new WebNode(rootPage);
	}
	
	public void setPostOrderScore(ArrayList<Keyword> keywords) throws IOException{
		setPostOrderScore(root, keywords);
	}
	
	private void setPostOrderScore(WebNode startNode, ArrayList<Keyword> keywords) throws IOException{
		//1. compute the score of children nodes postorder
		for(WebNode child : startNode.children){
			setPostOrderScore(child, keywords);
			
		}
		//**setNode score of startNode
			startNode.setNodeScore(new KeywordList(keywords));
		}
	
	public void eularPrintTree(){
		eularPrintTree(root);
	}
	
	private void eularPrintTree(WebNode startNode){
		int nodeDepth = startNode.getDepth();
		
		if(nodeDepth > 1) System.out.print("\n" + repeat("\t", nodeDepth-1));
		//print "("
		System.out.print("(");
		//print "name","score"
		System.out.print(startNode.webPage.name+","+startNode.nodeScore);
		
		//2.print child preorder
		for(WebNode child : startNode.children){
			eularPrintTree(child);
		}
		
		//print ")"
		System.out.print(")");
		
		
		
		if(startNode.isTheLastChild()) System.out.print("\n" + repeat("\t", nodeDepth-2));
		
	}

	
	
	private String repeat(String str,int repeat){
		String retVal  = "";
		for(int i=0;i<repeat;i++){
			retVal+=str;
		}
		return retVal;
	}

}
