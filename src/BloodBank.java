
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner; 
 
// Base class for donors 
class Donor 
{ 
    String name; 
    String bloodType; 
    int age; 
    int numDonations; 
} 
 
// Class for blood groups 
class BloodGroup 
{ 
    String bloodType; 
    int count; 
 
    // Constructor to set initial count to 0 
    BloodGroup(String bloodType) { 
        this.bloodType = bloodType; 
        this.count = 0; 
    } 
} 
 
// Class for recipients 
class Recipient 
{ 
    String name; 
    String bloodTypeRequired; 
    int age; 
} 
 
// Class to manage list of donors 
class DonorList 
{ 
    ArrayList<Donor> donors; 
 
    DonorList() { 
        donors = new ArrayList<Donor>(); 
    } 
 
    // Method to add a new donor 
    void addDonor(Donor donor) { 
        donors.add(donor); 
    } 
 
    // Method to count the number of donors with a certain blood group 
    int countBloodType(String bloodType) { 
        int count = 0; 
        for (Donor donor : donors) { 
            if (donor.bloodType.equals(bloodType)) { 
                count++; 
            } 
        } 
        return count; 
    } 
 
    // Method to check blood stock levels and print a message if any blood type is low 
    void checkBloodStock() { 
        String[] bloodTypes = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"}; 
        for (String bloodType : bloodTypes) { 
            int count = countBloodType(bloodType); 
            if (count < 5) { 
                System.out.println("Low stock of " + bloodType + " (" + count + " donors)"); 
            } 
        } 
    } 
} 
 
// Interface for Recipients -> RecipientListInterface
interface RecipientListInterface{
    void addRecipient(Recipient recipient);

    void findRecipient(String bloodType);

}
// Class to manage list of recipients which inherits from RecipientListInterface
class RecipientList implements RecipientListInterface
{ 
    ArrayList<Recipient> recipients; 
 
    RecipientList() { 
        recipients = new ArrayList<Recipient>(); 
    } 
 
    // Method to add a new recipient
    @Override
    public void addRecipient(Recipient recipient) {
        recipients.add(recipient); 
    } 
 
    // Method to find a recipient by blood type and print their details 
    public void findRecipient(String bloodType) {
        boolean found = false; 
        for (Recipient recipient : recipients) { 
            if (recipient.bloodTypeRequired.equals(bloodType)) { 
                System.out.println("Recipient: " + recipient.name + " (Blood type: " + recipient.bloodTypeRequired + ", Age: " + recipient.age + ")"); 
                found = true; 
            } 
        } 
        if (!found) { 
            System.out.println("No recipients found with blood type " + bloodType); 
            System.exit(0); 
        } 
    }
}
 
// Class to check donor eligibility based on age 
class Eligibility 
{ 
    void checkEligibility(int age) { 
        if (age < 18) { 
            System.out.println("Not eligible to donate blood"); 
        } else { 
            System.out.println("Eligible to donate blood"); 
        } 
    } 
} 
 
// Main class for the blood bank management system 
public class BloodBank { 
    public static void main(String[] args) { 
        Scanner scanner = new Scanner(System.in); 
        DonorList donorList = new DonorList(); 
        RecipientList recipientList = new RecipientList(); 
        Eligibility eligibility = new Eligibility(); 
 
        // Menu loop 
        while (true) { 
            System.out.println("Blood Bank Management System"); 
            System.out.println("-----------------------------"); 
            System.out.println("1. Add a new donor"); 
            System.out.println("2. Add a new recipient"); 
            System.out.println("3. List all donors"); 
            System.out.println("4. Check blood type stock levels"); 
            System.out.println("5. Search for donors by blood type"); 
            System.out.println("6. Exit"); 
            System.out.print("Enter your choice: "); 
 
            int choice = scanner.nextInt(); 
 
            switch (choice) { 
                case 1 -> { 
                    // Add a new donor 
                    Donor newDonor = new Donor(); 
                    System.out.print("Enter donor name: ");
                    String donorName = scanner.next();
                    newDonor.name = donorName;
                    scanner.nextLine();
                    System.out.print("Enter donor age: ");
                    int donorAge = scanner.nextInt();
                    newDonor.age = donorAge;
                    if (newDonor.age < 18) { 
                        System.out.println("Sorry mate under age!"); 
                        System.exit(0); 
                    } else { 
                        eligibility.checkEligibility(newDonor.age); 
                    } 
                    System.out.print("Enter donor blood type: ");
                    String donorBloodType = scanner.next();
                    newDonor.bloodType = donorBloodType;
                    System.out.print("Enter number of times donor has donated blood: ");
                    int donorNumDonations =  scanner.nextInt();
                    newDonor.numDonations = donorNumDonations;
                    donorList.addDonor(newDonor);

                    //*** Adding donor to -> file ***
                    try {
                        String fileName = "Donor.txt";
                        File file = new File(fileName);
                        FileWriter writer = new FileWriter(fileName, true);
                        writer.write(donorName  + "," + donorAge + "," + donorBloodType + "," + donorNumDonations + "\n");
                        writer.close();
                    } catch(IOException e) {
                        System.out.println("Exception");
                        e.printStackTrace();
                    }

                    System.out.println("Donor added successfully");
                } 
                case 2 -> { 
                    // Add a new recipient 
                    Recipient newRecipient = new Recipient(); 
                    System.out.print("Enter recipient name: "); 
                    newRecipient.name = scanner.next(); 
                    System.out.print("Enter recipient age: "); 
                    newRecipient.age = scanner.nextInt(); 
                    System.out.print("Enter recipient blood type required: "); 
                    newRecipient.bloodTypeRequired = scanner.next(); 
                    RecipientList r1=new RecipientList(); 
                    String type= newRecipient.bloodTypeRequired; 
                    r1.findRecipient(type); 
                    recipientList.addRecipient(newRecipient); 
                    System.out.println("Recipient added successfully"); 
                } 
                case 3 -> { 
                    // List all donors 
                    System.out.println("List of all donors:"); 
                    for (Donor donor : donorList.donors) { 
                        System.out.println("Donor: " + donor.name + " (Blood type: " + donor.bloodType + ", Age: " + donor.age + ", Donations: " + donor.numDonations + ")"); 
                    } 
                } 
                case 4 -> 
                    // Check blood type stock levels 
                        donorList.checkBloodStock(); 
                case 5 -> { 
                    // Search for donors by blood type 
                    System.out.print("Enter blood type to search for: "); 
                    String bloodType = scanner.next(); 
                    int count = donorList.countBloodType(bloodType); 
                    if (count > 0) { 
                        System.out.println("Found " + count + " donors with blood type " + bloodType); 
                    } else { 
                        System.out.println("No donors found with blood type " + bloodType); 
                    } 
                } 
                case 6 -> { 
                    // Exit 
                    System.out.println("Goodbye!"); 
                    System.exit(0); 
                } 
                default -> System.out.println("Invalid choice"); 
            } 
 
            System.out.println(); 
        } 
    } 
}
