package abc;

import abs.*;
import java.util.*;


public class Menu {

    Scanner scanner = new Scanner(System.in);
    static final int END_OF_INPUT = -1; //Minimum age requirement

    public Menu() {
    }

    public int getMinInterest() {
        System.out.println("Please enter the minimum interest you want to get for your loans.");
        System.out.println("If you don't have any preference, enter -1");
        boolean validInput = false;
        int minInterest = 0;
        while (!validInput) {
            try {
                minInterest = scanner.nextInt();
                if (0 < minInterest && minInterest < 100) {
                    validInput = true;
                }
                else if (minInterest == END_OF_INPUT) {
                    minInterest = 0;
                    validInput=true;
                }
            } catch (Exception e) {
                scanner.next();
            } finally {
                if (!validInput) {
                    System.out.println("Please enter a number between 0 to 100, or -1 if you don't have any preference.");
                }
            }
        }
        return minInterest;
    }

    public int getMinTimeForLoan(){
        System.out.println("Please enter the minimum time (to get back the money) for your loans.");
        System.out.println("If you don't have any preference, enter -1");

        boolean validInput = false;
        int minTime = 0;
        while (!validInput) {
            try {
                minTime = scanner.nextInt();
                if (0 < minTime) {
                    validInput = true;
                }
                if(minTime==END_OF_INPUT){
                    minTime=0;
                }
            } catch (Exception e) {
                scanner.next();
                System.out.println("Please enter an integer, more than 0, or -1 if you don't have any preference.");
            }
        }
        return minTime;
    }

    public void printMenu() {
        System.out.println("choose a number");
        System.out.println("1. read a file");
        System.out.println("2. get loans information");
        System.out.println("3. get clients information");
        System.out.println("4. load money to account");
        System.out.println("5. Withdraw money to account");
        System.out.println("6. Activate inlay");
        System.out.println("7. promote timeline and make payments");
        System.out.println("8. Exit");
    }

    public boolean FileNotExist() {
        System.out.println("Error! File does not exist.");
        System.out.println("If you want to go back to menu - press 1");
        System.out.println("If you want to try load file again - press 2");
        boolean validInput = false, loadAgain = false;
        int userChoice = 0;
        while (!validInput) {
            try {
                userChoice = scanner.nextInt();
                if (userChoice == 1) {
                    loadAgain = false;
                    validInput = true;
                } else if (userChoice == 2) {
                    loadAgain = true;
                    validInput = true;
                }
            } catch (Exception e) {
                scanner.next();
            } finally {
                if (!validInput) {
                    System.out.println("Invalid input. Please try again.");
                }
            }
        }
        return loadAgain;
    }

    public boolean verifyExit() {
        System.out.println("You just arrived! Are you sure you want to exit??");
        System.out.println("1.NO - I want to stay!!");
        System.out.println("2.YES - Exit.");
        int userChoice = 0;
        boolean wantToStay = true;
        boolean validInput = false;
        while (!validInput) {
            try {
                if (userChoice == 2) {
                    validInput = true;
                    wantToStay = false;
                }
                userChoice = scanner.nextInt();
            } catch (Exception e) {
                scanner.next();
            }
            if (userChoice != 1) {
                System.out.println("Please choose 1 for stay or 2 for exit!");
            } else {
                wantToStay = true;
                validInput = true;
            }
        }
        return wantToStay;
    }

    public List<String> chooseCategory(List<String> categories) {
        printCategories(categories);
        boolean validInput = false;
        List<String> userCategoriesChoice = new ArrayList<String>();
        int numberOfCategories = categories.size(), currCategory = 0;
        while (currCategory != -END_OF_INPUT) {
            currCategory = scanCategoryNumber(numberOfCategories);
            if (currCategory != END_OF_INPUT)
                userCategoriesChoice.add(categories.get(currCategory));
        }
        if (userCategoriesChoice.size() == 0)
            userCategoriesChoice = categories;
        return userCategoriesChoice;
    }

    public int scanCategoryNumber(int maxNumOfCategories) {
        boolean validInput = false;
        int numberOfCtegory = 0;
        while (!validInput) {
            try {
                numberOfCtegory = scanner.nextInt();
                if (numberOfCtegory < maxNumOfCategories + 1) {
                    validInput = true;
                } else {
                    System.out.println("Invalid input! There are only " + maxNumOfCategories + "categories.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter an integer between 1 to " + maxNumOfCategories + ".");
                scanner.next();
            }
        }
        return (numberOfCtegory - 1);
    }

    public void printCategories(List<String> categories) {
        System.out.println("Please choose categories from the next list:");
        System.out.println("Enter the categories numbers, and finish with -1.");
        System.out.println("If you don't have any preference, please enter -1");
        int index = 0;
        for (String category : categories) {
            System.out.println(index + ". " + category);
            index++;
        }
    }

    public void printClientsNames(Collection<Client> clients, boolean includeBalance) {
        int clientIndex = 1;
        for (Client client : clients) {
            System.out.println(clientIndex + ". " + client.getFullName());
            if (includeBalance) {
                System.out.println("Client balance: " + client.getCurrBalance());
            }
            clientIndex++;
        }
    }

    public int chooseAmountByBalance(int currBalance) {
        boolean validInput = false;
        int amount = 0;
        while (!validInput) {
            amount = scanAmountFromUser();
            if (amount <= currBalance) {
                validInput = true;
            } else {
                System.out.println("Current amount in balance is " + currBalance + ". You can't choose more than " + currBalance + ".");
                System.out.println("Please try again");
            }
        }
        return amount;
    }

    public int scanAmountFromUser() {
        boolean validInput = false;
        int amountToCharge = 0;
        while (!validInput) {
            try {
                amountToCharge = scanner.nextInt();
                if (amountToCharge > 0) {
                    validInput = true;
                }
            } catch (InputMismatchException e) {
                scanner.next();
            }
            if (!validInput) {
                System.out.println("Invalid input! Please enter a positive integer!");
            }
        }
        return amountToCharge;
    }

    public int getClientNumber(int maxClients) {
        boolean validInput = false;
        int numberOfClient = 0;
        while (!validInput) {
            try {
                numberOfClient = scanner.nextInt();
                if (numberOfClient > 0 && numberOfClient < maxClients) {
                    validInput = true;
                }
            } catch (Exception e) {
                scanner.next();
            } finally {
                if (!validInput) {
                    System.out.println("Invalid Input!! Please enter a number between 1 to " + maxClients);
                }
            }
        }
        return (numberOfClient - 1);
    }

    public boolean checkAmount(int number) {
        return (number > 0);
    }

    public void printOneClientInfo(Client client) {
        System.out.println(client.getFullName() + ":");
        System.out.println("Client account movements:");
        for (int i = 0; i < Globals.worldTime; i++) {
            Set<Movement> movements = client.getMovementsByTime(i);
            if (movements != null) {
                printMovementsInfo(movements);
            }
        }
        System.out.println("loans to get money:");
        for (Loan loan : client.getLoanSet("asGiver")) {
            printSingleLoanInfo(loan);
        }
        System.out.println("loans give money");
        for (Loan loan : client.getLoanSet("asBorrower")) {
            printSingleLoanInfo(loan);
        }

    }

    public void printMovementsInfo(Set<Movement> movements) {
        System.out.println("Time: " + Globals.worldTime + ":");
        for (Movement movement : movements) {
            System.out.println("Account balance before abs.movement: " + movement.getAmountBeforeMovement());
            System.out.println(movement.getKindOfExecute() + " " + movement.getAmount());
            System.out.println("Account balance after abs.movement: " + movement.getAmountBeforeMovement());
        }

    }

    public void printSingleLoanInfo(Loan curLoan) {
        System.out.println("Loans ID: " + curLoan.getLoansID());
        System.out.println("Owner: " + curLoan.getOwnersName());
        System.out.println("Category: " + curLoan.getCategory());
        System.out.println("Amount: " + curLoan.getOriginalAmount() + "Original total time units: " + curLoan.getTotalTU());
        System.out.println("Interest: " + curLoan.getInterestRate() + "Rate: " + curLoan.getPace());
        Loan.Status status = curLoan.getStatus();
        System.out.println("Status: " + status);

        switch (status) {
            case RISK:
                System.out.println("status: in risk");
                break;
            case ACTIVE:
                System.out.println("status: active");
                //curLoan.getGivers();
                // need to print each lender's name and invesment
                System.out.println("abs.loan was activated at :" + curLoan.getStartingTimeUnit());
                System.out.println("next abs.payment is at :" + curLoan.getNextPayment());
                break;
            case PENDING:
                System.out.println("status: pending");

                //curLoan.getGivers();
                // need to print each lender's name and invesment
                System.out.println("current amount is :" + curLoan.getAmountCollected());
                System.out.println("amount left to activate abs.loan :" + (curLoan.getOriginalAmount() - curLoan.getAmountCollected()));
                break;
            case FINISHED:
                System.out.println("status: finished");

                break;
        }
    }

    public void printClientsInfo() {
        /*
        for (Client client : bank.getClients()) {
            printOneClientInfo(client);
        }
*/
    }

    public int getUserChoice(boolean fileInSystem) {
        boolean validInput = false;
        int usersChoice = 0;
        while (!validInput) {
            try {
                usersChoice = scanner.nextInt();
                if (usersChoice < 1 || usersChoice > 8) {
                    System.out.println("Invalid input, Please enter an integer between 1 - 8.");
                } else if (usersChoice != 1 && !fileInSystem) {
                    System.out.println("Invalid input, There is no file scanned. Please choose again.");
                } else {
                    validInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, Please enter an integer between 1 - 8.");
                scanner.next();
            }
        }
        return usersChoice;
    }
}