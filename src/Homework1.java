
import java.util.Stack;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultTreeCellRenderer;



public class Homework1 extends JPanel
		implements TreeSelectionListener {
    static String post;
    static tree t;
    JTree tree;
    JEditorPane htmlPane;
    DefaultMutableTreeNode CheckNode;
    DefaultMutableTreeNode top;

    public Homework1(){
		super(new GridLayout(1,0));

		//Create the nodes.
		top = new DefaultMutableTreeNode(t.treeroot.x);
		createNodes(top,t.treeroot);

		//Create a tree that allows one selection at a time.
		tree = new JTree(top);
		tree.getSelectionModel().setSelectionMode
				(TreeSelectionModel.SINGLE_TREE_SELECTION);

		//Listen for when the selection changes.
		tree.addTreeSelectionListener(this);

		tree.putClientProperty("JTree.lineStyle","None");
		ImageIcon NodeIcon =  createImageIcon("middle.gif");
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		renderer.setOpenIcon(NodeIcon);
		renderer.setClosedIcon(NodeIcon);
		tree.setCellRenderer(renderer);




		//Create the scroll pane and add the tree to it.
		JScrollPane treeView = new JScrollPane(tree);

		//Create the HTML viewing pane.
		htmlPane = new JEditorPane();

		JScrollPane htmlView = new JScrollPane(htmlPane);

		//Add the scroll panes to a split pane.
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setTopComponent(treeView);
		splitPane.setBottomComponent(htmlView);

		Dimension minimumSize = new Dimension(100, 50);
		htmlView.setMinimumSize(minimumSize);
		treeView.setMinimumSize(minimumSize);
		splitPane.setDividerLocation(100);
		splitPane.setPreferredSize(new Dimension(500, 300));

		//Add the split pane to this panel.
		add(splitPane);
	}
    
	public static void main(String[] args) {
                
        post = "251-*32*+";
        if(args.length>0)post=args[0];
        t = new tree(post);
        t.TreeStack();
        t.inorder(t.treeroot);
        t.infix(t.treeroot);
        t.calculate(t.treeroot);
         System.out.printf( " = " + t.sum);
  //  System.out.println(t.sum);
  
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}

            private void createAndShowGUI() {
               //Create and set up the window.
		JFrame frame = new JFrame("Homework1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Create and set up the content pane.
		Homework1 newContentPane = new Homework1();
		newContentPane.setOpaque(true); //content panes must be opaque
		frame.setContentPane(newContentPane);

		//Display the window.
		frame.pack();
		frame.setVisible(true);
            }
		});
               
}

    @Override
    public void valueChanged(TreeSelectionEvent tse) {
            		CheckNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		tree.getLastSelectedPathComponent();
		if(CheckNode == null){
			return;
		}
		String text = inorder(CheckNode);
		if(!CheckNode.isLeaf()) text += "=" + calculate(CheckNode,t.treeroot);
		htmlPane.setText(text);
    }

    private ImageIcon createImageIcon(String middlegif) {
        java.net.URL imgURL = Homework1.class.getResource(middlegif);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + middlegif);
			return null;
		}
    }

    private void createNodes(DefaultMutableTreeNode top, Node treeroot) {
                if(treeroot.left!=null)
		{
			DefaultMutableTreeNode TopLeft = new DefaultMutableTreeNode(treeroot.left.x);
			top.add(TopLeft);
			createNodes(TopLeft,treeroot.left);
		}
		if(treeroot.right!=null)
		{
			DefaultMutableTreeNode TopRight = new DefaultMutableTreeNode(treeroot.right.x);
			top.add(TopRight);
			createNodes(TopRight,treeroot.right);
		}
    }
        
           
public static class tree {
    
    public String postfix;
    public Node treeroot;
    public int sum = 0;
    public Node NowRoot;
    
    tree(String postfix){
        this.postfix = postfix;
    }
   
    public void inorder(Node node){
        if (node == null){
            return ;
        }
        else
            inorder(node.left);
            inorder(node.right);
    }
    
    public boolean operater(char x){
        if(x == '+'|| x == '-'|| x == '*'|| x == '/'){
            return true;
        }
        else{
            return false;
        } 
    }
    
    
    
    public int calculate (Node node){
    
        if((operater(node.x))){
            switch (node.x){
            case '+': sum = calculate(node.left) + calculate(node.right) ; break;
            case '-': sum = calculate(node.left) - calculate(node.right) ; break;
            case '*': sum = calculate(node.left)  * calculate(node.right) ; break;
            case '/': sum = calculate(node.left)  / calculate(node.right) ; break;
        }
    }
        else{
            return num(node.x);
        }  
        return sum;
    }
    
    
    public void TreeStack(){
        
        
        final Stack<Node> node = new Stack<Node>();
            for (int i = 0; i < postfix.length(); i++){
                char x  = postfix.charAt(i);
     
                    if (operater(x)){
                     Node right = node.pop();
                     Node left = node.pop();
                     NowRoot = new Node(x);
                     NowRoot.left = left;
                     NowRoot.right = right;
                     node.push(NowRoot);
                     }
                    else{
                         node.add(new Node(x));
                     }       
            }
        treeroot = node.pop();
    }
    
    
    public void infix(Node n){
        System.out.println(n);
    }
    
    
    
    private int num(char num)
    {
        return num - '0';
    }  

    }


public static class Node {
    Node right,left;
    char x;
    Node (char x){
        this.x = x;
        this.left = null;
        this.right = null;
    }
    public String toString() {
        return (right == null && left == null) ? Character.toString(x) : "(" + left.toString()+ x + right.toString() + ")";
    }
}

public String inorder(DefaultMutableTreeNode node) { 
		if (node == null) return "Root Is Null";
		if(node == CheckNode && !node.isLeaf()) { 
			return 	inorder(node.getNextNode()) + node.toString() + inorder(node.getNextNode().getNextSibling());
		}else if(t.operater(node.toString().charAt(0)) && node != top) {
			return "(" + inorder(node.getNextNode()) + node.toString() + inorder(node.getNextNode().getNextSibling()) + ")";
		}else {
			return node.toString(); 
		}
	}

public int calculate(DefaultMutableTreeNode node, Node n) {
		if(node.isLeaf()) return Integer.parseInt(node.toString());
		switch(node.toString()) {
                    case "+" : return calculate(node.getNextNode(),n.left) + calculate(node.getNextNode().getNextSibling(),n.left);
                    case "-" : return calculate(node.getNextNode(),n.left) - calculate(node.getNextNode().getNextSibling(),n.left);
                    case "*" : return calculate(node.getNextNode(),n.left) * calculate(node.getNextNode().getNextSibling(),n.left);
                    case "/" : return calculate(node.getNextNode(),n.left) / calculate(node.getNextNode().getNextSibling(),n.left);
		}
        return 0;
	
}
}


     