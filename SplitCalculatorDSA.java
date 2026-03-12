import java.util.*;

// CO2: Using Data Structures (Class to store expense details)
class Expense {
    String payer;
    double amount;
    List<String> participants;

    Expense(String payer, double amount, List<String> participants) {
        this.payer = payer;
        this.amount = amount;
        this.participants = participants;
    }
}

public class SplitCalculatorDSA {

    public static void main(String[] args) {

        // CO1: Basic Programming Concepts (Input using Scanner)
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of friends: ");
        int n = sc.nextInt();
        sc.nextLine();

        // CO2: Using ArrayList Data Structure
        List<String> friends = new ArrayList<>();

        for(int i = 0; i < n; i++) {
            System.out.print("Enter friend name: ");
            friends.add(sc.nextLine());
        }

        System.out.print("Enter number of expenses: ");
        int e = sc.nextInt();
        sc.nextLine();

        // CO2: ArrayList to store expense objects
        List<Expense> expenses = new ArrayList<>();

        for(int i = 0; i < e; i++) {

            System.out.println("\nExpense " + (i+1));

            System.out.print("Who paid: ");
            String payer = sc.nextLine();

            System.out.print("Amount: ");
            double amount = sc.nextDouble();
            sc.nextLine();

            System.out.print("Number of participants: ");
            int p = sc.nextInt();
            sc.nextLine();

            List<String> participants = new ArrayList<>();

            for(int j = 0; j < p; j++) {
                System.out.print("Participant name: ");
                participants.add(sc.nextLine());
            }

            expenses.add(new Expense(payer, amount, participants));
        }

        // CO3: Calling algorithm to calculate balances
        calculateSettlement(friends, expenses);
    }

    // CO3: Algorithm to compute balance after splitting expenses
    public static void calculateSettlement(List<String> friends, List<Expense> expenses){

        // CO2: HashMap used to track balance of each friend
        HashMap<String, Double> balance = new HashMap<>();

        for(String f : friends)
            balance.put(f, 0.0);

        for(Expense ex : expenses){

            // CO3: Expense splitting logic
            double split = ex.amount / ex.participants.size();

            for(String p : ex.participants)
                balance.put(p, balance.get(p) - split);

            balance.put(ex.payer, balance.get(ex.payer) + ex.amount);
        }

        System.out.println("\nFinal Balances:");

        for(String f : balance.keySet())
            System.out.println(f + " : " + balance.get(f));

        // CO4: Optimize transactions using greedy approach
        simplifyDebts(balance);
    }

    // CO4: Greedy Algorithm using Priority Queues
    public static void simplifyDebts(HashMap<String, Double> balance){

        // CO2: PriorityQueue (Heap) for creditors
        PriorityQueue<Map.Entry<String,Double>> creditors =
                new PriorityQueue<>((a,b)->Double.compare(b.getValue(),a.getValue()));

        // CO2: PriorityQueue (Heap) for debtors
        PriorityQueue<Map.Entry<String,Double>> debtors =
                new PriorityQueue<>((a,b)->Double.compare(a.getValue(),b.getValue()));

        for(Map.Entry<String,Double> entry : balance.entrySet()){

            if(entry.getValue() > 0)
                creditors.add(entry);

            else if(entry.getValue() < 0)
                debtors.add(entry);
        }

        System.out.println("\nSettlement Transactions:");

        while(!creditors.isEmpty() && !debtors.isEmpty()){

            Map.Entry<String,Double> cred = creditors.poll();
            Map.Entry<String,Double> debt = debtors.poll();

            // CO4: Greedy decision to minimize transactions
            double amount = Math.min(cred.getValue(), -debt.getValue());

            System.out.println(debt.getKey() + " pays " + cred.getKey() + " ₹" + amount);

            cred.setValue(cred.getValue() - amount);
            debt.setValue(debt.getValue() + amount);

            if(cred.getValue() > 0)
                creditors.add(cred);

            if(debt.getValue() < 0)
                debtors.add(debt);
        }

        // CO5: Real-world application similar to Splitwise
    }
}