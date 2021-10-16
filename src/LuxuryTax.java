public class LuxuryTax extends IncomeTax{

    public void takeMoney(Player player) {
        player.removefromBalance(100);
    }
}
