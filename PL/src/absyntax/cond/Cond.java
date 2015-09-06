package absyntax.cond;

import absyntax.Generator;
import absyntax.Node;

public abstract class Cond extends Node {

    public abstract CondTags getTags();

    public abstract Cond not();

    public class CondTags{

        private String trueTag, falseTag;

        public CondTags(){
            this.trueTag = Generator.getInstance().newTag();
            this.falseTag = Generator.getInstance().newTag();
        }

        public CondTags(String trueTag, String falseTag){
            this.trueTag = trueTag;
            this.falseTag = falseTag;
        }

        public CondTags not(){
            return new CondTags(this.falseTag, this.trueTag);
        }

        public String getTrueTag() {
            return trueTag;
        }

        public String getFalseTag() {
            return falseTag;
        }
    }
}
