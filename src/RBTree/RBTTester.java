package RBTree;
import static org.junit.Assert.*;

import org.junit.Test;


public class RBTTester {

	@Test
    //Test the Red Black Tree
	public void test() {
		RedBlackTree rbt = new RedBlackTree();
        rbt.insert("D");
        rbt.insert("B");
        rbt.insert("A");
        rbt.insert("C");
        rbt.insert("F");
        rbt.insert("E");
        rbt.insert("H");
        rbt.insert("G");
        rbt.insert("I");
        rbt.insert("J");
        assertEquals("DBACFEHGIJ", makeString(rbt));
        String str=     "Color: 1, Key:D Parent: \n"+
                        "Color: 1, Key:B Parent: D\n"+
                        "Color: 1, Key:A Parent: B\n"+
                        "Color: 1, Key:C Parent: B\n"+
                        "Color: 1, Key:F Parent: D\n"+
                        "Color: 1, Key:E Parent: F\n"+
                        "Color: 0, Key:H Parent: F\n"+
                        "Color: 1, Key:G Parent: H\n"+
                        "Color: 1, Key:I Parent: H\n"+
                        "Color: 0, Key:J Parent: I\n";
		assertEquals(str, makeStringDetails(rbt));
            
    }
    
    //add tester for spell checker
    
    public static String makeString(RedBlackTree t)
    {
       class MyVisitor implements RedBlackTree.Visitor {
          String result = "";
          public void visit(RedBlackTree.Node n)
          {
             result = result + n.key;
          }
       };
       MyVisitor v = new MyVisitor();
       t.preOrderVisit(v);
       return v.result;
    }

    public static String makeStringDetails(RedBlackTree t) {
        {
            class MyVisitor implements RedBlackTree.Visitor {
                StringBuilder sb = new StringBuilder();
                int i = 0;
                public void visit(RedBlackTree.Node n) {
                    if (!(n.key).equals("")) {
                        if (i == 0) {
                            sb.append("Color: ").append(n.color).append(", Key:").append(n.key).append(" Parent: ").append("").append("\n");
                        } else {
                            sb.append("Color: ").append(n.color).append(", Key:").append(n.key).append(" Parent: ").append(n.parent.key).append("\n");
                        }
                        i++;
                    }
                }
            }
            MyVisitor v = new MyVisitor();
            t.preOrderVisit(v);
            return v.sb.toString();
        }
    }    
 }
  
