package csci3002.project.game;

public class Cat {
    public void talk() {
        System.out.println("Character is talking.");
    }

    public boolean hasOrdered;
    public boolean receivedRightOrder;
    public MenuItem order;


    public Cat(MenuList menu) {
        this.hasOrdered = false;
        this.receivedRightOrder = false;
        this.order = menu.getRandomMenuItem();
    }


    public MenuItem getOrder() { return order; }

}

