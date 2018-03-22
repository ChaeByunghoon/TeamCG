package org.apache.synapse.commons.beanstalk.enterprise;
public class Test{

    private String cont = "shre";
    ArrayList good = new ArrayList<>();
    Vector<String> hi = new Vector<String>();
    int i = 3;

    public HttpSession getSession (String test) {

        good.add(1);
        hi.add("Hello");

        try {
            fileOutController.fileOut();
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (i){
            case 0:
                return super.getSession();
            case 1:
                return null;

            default:
                return null;
        }


        return super.getSession();

    }
}