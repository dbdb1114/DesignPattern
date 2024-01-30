package part4_factory_method_pattern.id_card_factory;

import part4_factory_method_pattern.framework.Product;

public class IDCard extends Product {
    private String owner;
    private int cardNo;

    IDCard(String owner, int cardNo) {
        System.out.println(owner + "의 카드를 만듭니다.");
        this.owner = owner;
        this.cardNo = cardNo;
    }

    @Override
    public void use(){
        System.out.println(this + "을 사용합니다.");
    }

    @Override
    public String toString() {
        return "[IDCard:" + owner + "]";
    }

    public String getOwner() {
        return owner;
    }

    public int getCardNo() {
        return cardNo;
    }

    public void setCardNo(int cardNo) {
        this.cardNo = cardNo;
    }

}
