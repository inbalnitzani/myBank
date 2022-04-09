package ui;
import bank.Global;
import dto.*;
import loan.Status;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Menu {

    Scanner scanner = new Scanner(System.in);
    static final int END_OF_INPUT = -1;

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
                if (END_OF_INPUT <= minInterest && minInterest < 100) {
                    validInput = true;
                    if (minInterest == END_OF_INPUT) {
                        minInterest = 0;
                    }
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

    public int getMinTimeForLoan() {
        System.out.println("Please enter the minimum time (to get back the money) for your loans.");
        System.out.println("If you don't have any preference, enter -1");
        boolean validInput = false;
        int minTime = 0;
        while (!validInput) {
            try {
                minTime = scanner.nextInt();
                if (END_OF_INPUT <= minTime) {
                    validInput = true;
                    if (minTime == END_OF_INPUT) {
                        minTime = 0;
                    }
                }
            } catch (Exception e) {
                scanner.nextLine();
            } finally {
                if (!validInput) {
                    System.out.println("Invalid input! Please enter an integer, or -1 if you don't have any preference.");
                }
            }
        }
        return minTime;
    }

    public List<LoanDTO> chooseLoansToInvest(List<LoanDTO> optionalLoans) {
        List<LoanDTO> loansToInvest = null;
        if (optionalLoans.isEmpty()) {
            System.out.println("There are no loans suitable for your requirements. ");
        } else {
            System.out.println("Please insert the number of all loans you would like to invest,and finish with -1:");
            System.out.println("If you don't want any loan, insert -1.");
            printLoansInfo(optionalLoans);
            Set<Integer> loansIndex = scanLoansFromUser(optionalLoans.size());
            loansToInvest = createListLoanDTOToInvest(optionalLoans, loansIndex);
        }
        return loansToInvest;
    }

    public int chooseWayToGetDataInfo() {
        System.out.println("Choose one of the next source options to load information:");
        System.out.println("1. XML file");
        System.out.println("2. Saved file with old state");
        int userChoice = 0;
        boolean invalidInput = false;
        while (!invalidInput) {
            try {
                userChoice = scanner.nextInt();
                if (userChoice == 1 || userChoice == 2) {
                    invalidInput = true;
                }
            } catch (Exception e) {
                scanner.nextLine();
            } finally {
                if (!invalidInput)
                    System.out.println("Invalid input.Please choose 1 or 2.");
            }
        }
        return userChoice;
    }

    public void printMenu() {
        System.out.println("\nchoose a number");
        System.out.println("1. read a file");
        System.out.println("2. get loans information");
        System.out.println("3. get clients information");
        System.out.println("4. load money to account");
        System.out.println("5. Withdraw money to account");
        System.out.println("6. Activate inlay");
        System.out.println("7. Promote timeline and make payments");
        System.out.println("8. Save system state to a file");
        System.out.println("9. Exit");
    }

    public boolean verifyExit() {
        System.out.println("You just arrived! Are you sure you want to exit??");
        System.out.println("1.NO - I want to stay!!");
        System.out.println("2.YES - Exit.");
        boolean wantToExit = true, validInput = false;
        while (!validInput) {
            try {
                int userChoice = scanner.nextInt();
                if (userChoice == 2 || userChoice == 1) {
                    validInput = true;
                    if (userChoice == 1) {
                        wantToExit = false;
                    }
                }
            } catch (Exception e) {
                scanner.nextLine();
            } finally {
                if (!validInput) {
                    System.out.println("Please choose 1 for stay or 2 for exit!");
                }
            }
        }
        return wantToExit;
    }

    public boolean checkTryAgain(String fileName) {
        System.out.println("Do you want to try another file?");
        System.out.println("Press 1 for try again, 2 for go back to menu.");
        boolean validInput = false, tryAgain = false;
        while (!validInput) {
            try {
                int userChoice = scanner.nextInt();
                if (userChoice == 1 || userChoice == 2) {
                    validInput = true;
                    if (userChoice == 1) {
                        tryAgain = true;
                    }
                }
            } catch (Exception e) {
                scanner.nextLine();
            } finally {
                if (!validInput) {
                    System.out.println("Invalid input. Please insert 1 for try again, 2 for continue.");
                }
            }
        }
        return tryAgain;
    }

    public String getFileNameFromUser() {
        String filePath = null;
        boolean validInput = false;
        filePath = scanner.nextLine();
        while (!validInput) {
            try {
                filePath = scanner.nextLine();
                validInput = true;
            } catch (Exception e) {
                System.out.println("Invalid input! Please try again");
            }
        }
        return filePath;
    }

    public List<LoanDTO> createListLoanDTOToInvest(List<LoanDTO> optional, Set<Integer> indexOfLoans) {
        List<LoanDTO> loansToInvest = new ArrayList<LoanDTO>();
        for (int index : indexOfLoans) {
            loansToInvest.add(optional.get(index));
        }
        return loansToInvest;
    }

    public void updateUserInvest(int amountToInvest, int amountLeft) {
        if (amountLeft == 0) {
            System.out.println("Your investments have been successfully completed");
        } else {
            if (amountToInvest == amountLeft) {
                System.out.println("Sorry, an unknown error has occurred, please try again..");
            } else {
                System.out.println("Invested " + (amountToInvest - amountLeft) + " out of " + amountToInvest + " successfully");
                System.out.println("There is no more money to invest in loans that fit your requirements");
            }
        }
    }

    public Set<Integer> scanLoansFromUser(int maxLoans) {
        int currLoan = 0;
        Set<Integer> loansToInvestIndex = new HashSet<Integer>();
        while (currLoan != END_OF_INPUT && loansToInvestIndex.size() < maxLoans) {
            try {
                currLoan = scanner.nextInt();
                if (currLoan <= maxLoans && currLoan > 0) {
                    if (loansToInvestIndex.contains(currLoan - 1)) {
                        System.out.println("You have already chose this loan, please choose another loan, or finish with -1.");
                    } else loansToInvestIndex.add(currLoan - 1);
                } else if (currLoan != END_OF_INPUT) {
                    System.out.println("Invalid input. Please insert an integer between 1 to " + maxLoans + ", or -1 for continue without loans.");
                }
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Please insert an integer between 1 to " + maxLoans + ".");
            }
        }
        return loansToInvestIndex;
    }

    public void printLoansInfo(List<LoanDTO> optionalLoans) {
        int sizeOfList = optionalLoans.size();
        for (int index = 0; index < sizeOfList; index++) {
            System.out.println("loan number " + (index + 1) + ": ");
            printSingleLoanInfo(optionalLoans.get(index));
            System.out.println("");
        }
    }

    public Set<String> chooseCategory(List<String> existCategories) {
        printCategories(existCategories);
        String categoryDTO;
        boolean validInput = false;
        Set<String> userCategoriesChoice = new HashSet<String>();
        int sumCategories = existCategories.size(), currCategory = 0;
        while (currCategory != (END_OF_INPUT - 1) && userCategoriesChoice.size() < sumCategories) {
            currCategory = scanCategoryIndex(sumCategories);

            if (currCategory != (END_OF_INPUT - 1)) {
                categoryDTO = existCategories.get(currCategory);
                if (userCategoriesChoice.contains(categoryDTO)) {
                    System.out.println("You have already chose this category. Please choose again.");
                } else {
                    userCategoriesChoice.add(categoryDTO);
                }
            }
        }
        if (userCategoriesChoice.size() == 0) {
            for (String category : existCategories) {
                userCategoriesChoice.add(category);
            }
        }
        return userCategoriesChoice;
    }

    public int scanCategoryIndex(int maxNumOfCategories) {
        boolean validInput = false;
        int numberOfCategory = 0;
        while (!validInput) {
            try {
                numberOfCategory = scanner.nextInt();
                if ((numberOfCategory > 0 && numberOfCategory < maxNumOfCategories + 1) || (numberOfCategory == -1)) {
                    validInput = true;
                } else {
                    System.out.println("Invalid input! There are only " + maxNumOfCategories + " categories.");
                    System.out.println("Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter an integer between 1 to " + maxNumOfCategories + ".");
                scanner.nextLine();
            }
        }
        return (numberOfCategory - 1);
    }

    public void printCategories(List<String> categories) {
        System.out.println("Please choose categories from the next list:");
        int numOfCategories = categories.size();

        for (int index = 1; index <= numOfCategories; index++) {
            System.out.println(index + ". " + categories.get(index - 1));
        }
        System.out.println("Enter the categories numbers, and finish with -1.");
        System.out.println("If you don't have any preference, please enter -1");
    }

    public void printClientsNames(Collection<ClientDTO> clients, boolean includeBalance) {
        int clientIndex = 1;
        for (ClientDTO client : clients) {
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
                scanner.nextLine();
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
                if (numberOfClient > 0 && numberOfClient < maxClients + 1) {
                    validInput = true;
                }
            } catch (Exception e) {
                scanner.nextLine();
            } finally {
                if (!validInput) {
                    System.out.println("Invalid Input!! Please enter a number between 1 to " + maxClients);
                }
            }
        }
        return (numberOfClient - 1);
    }

    public void printSingleLoanInfo(LoanDTO loan) {
        System.out.println("Loans ID: " + loan.getLoansID());
        System.out.println("Owner: " + loan.getOwner());
        System.out.println("Category: " + loan.getCategory());
        System.out.println("Amount: " + loan.getOriginalAmount() + ",  Original total time units for loan: " + loan.getTotalYazTime());
        System.out.println("Interest: " + loan.getInterestRate() + ",  Rate of payments: " + loan.getPace());
        Status status = loan.getStatus();
        System.out.println("Status: " + status);

        switch (status) {
            case RISK:
                printLenderDetail(loan);
                printActiveLoanDetails(loan);
                printPaymentUnpaid(loan);
                break;
            case ACTIVE:
                printLenderDetail(loan);
                printActiveLoanDetails(loan);
                break;
            case FINISHED:
                printPayingTime(loan);
                //break;
            case PENDING:
                printLenderDetail(loan);
                printMoneyPayed(loan);
                break;
        }
    }

    public void printPayingTime(LoanDTO loan) {
        System.out.println("First payment: " + loan.getFirstPaymentTime() + ", Last pament: " + loan.getLastPaymentTime() + ".");
    }

    public void printPaymentUnpaid(LoanDTO loan) {
        Map<Integer, PaymentDTO> paymentDTOMap = loan.getPayments();
        List<Integer> unPaidTimes=new ArrayList<>();
        int firstTime=loan.getFirstPaymentTime(), pace=loan.getPace();
        System.out.println("There are "+unPaidTimes.size()+" unpaid payments in times: ");
        for (int i =firstTime ; i <= Global.worldTime; i += pace) {
            if (paymentDTOMap.get(i).getActualPaidTime() == 0)
                unPaidTimes.add(i);
        }
        System.out.println(unPaidTimes);
        System.out.println("Total amount missing: "+(loan.getTotalMoneyForPayingBack()-loan.getAmountPaidBack()));
    }

    public void printPaymentPaid(LoanDTO loan) {
        List<PaymentDTO> paid = loan.getPayments().values().stream()
                .filter(payment -> payment.getActualPaidTime() != 0).collect(Collectors.toList());

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(3);
        double fundPayBack = 0, interestPayBack = 0;
        if (!paid.isEmpty()) {
            System.out.println("Payments made so far:");
            for (PaymentDTO payment : paid) {
                double fund = payment.getFund();
                double interest = payment.getInterestPart();
                System.out.println("Payment time: " + payment.getActualPaidTime() + ", fund: " + df.format(fund) + ", interest: " + df.format(interest) + " .");
                System.out.println("Total amount paid: " + df.format(payment.getAmount()));
                fundPayBack += fund;
                interestPayBack += interest;
            }
        } else {
            System.out.println("Payments have not yet been refunded");
        }
        double originalFundAmount = loan.getOriginalAmount();
        double interestLeftToPay = loan.getTotalMoneyForPayingBack() - originalFundAmount - interestPayBack;
        double fundLeftToPay = originalFundAmount - fundPayBack;
        System.out.println("Total fund paid back: " + df.format(fundPayBack) + ", Total interest paid back: " + df.format(interestPayBack) + ".");
        System.out.println("Fund amount left to pay: " + df.format(fundLeftToPay) + ", Interest amount left to pay: " + df.format(interestLeftToPay) + " .");
    }

    public void printActiveLoanDetails(LoanDTO loan) {
        System.out.println("Active time is : " + loan.getActiveTime());
        System.out.println("Next payment time: " + loan.getNextPaymentTime());
        printPaymentPaid(loan);

    }

    public void printMoneyPayed(LoanDTO loan) {
        System.out.println("Total amount invested :" + loan.getAmountCollected());
        int amountLeft = loan.getOriginalAmount() - loan.getAmountCollected();
        System.out.println("Total amount to invest in order to become active: " + amountLeft);
    }

    public void printLenderDetail(LoanDTO loan) {
        System.out.println("List of lenders on this loan:");
        List<PayBackDTO> payBackDTO = loan.getPayBacks();
        for (PayBackDTO payBack : payBackDTO) {
            System.out.println(payBack.getGiversName() + "- " + payBack.getAmountInvested() + " NIS");
        }
    }

    public int getUserChoice(boolean fileInSystem) {
        boolean validInput = false;
        int usersChoice = 0;
        while (!validInput) {
            try {
                usersChoice = scanner.nextInt();
                if (usersChoice < 1 || usersChoice > 9) {
                    System.out.println("Invalid input, Please enter an integer between 1 - 9.");
                } else if (usersChoice != 1 && usersChoice != 9 && !fileInSystem) {
                    System.out.println("Invalid input, There is no file scanned. Please choose again.");
                } else {
                    validInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, Please enter an integer between 1 - 9.");
                scanner.nextLine();
            }
        }
        return usersChoice;
    }

    public void printClientInfo(List<ClientDTO> clientDTOS) {
        for (ClientDTO clientDTO : clientDTOS) {
            System.out.println("\n" + clientDTO.getFullName() + ":");
            printClientDetails(clientDTO);
        }
    }

    public void printClientDetails(ClientDTO clientDTO) {
        printAllMovements(clientDTO.getMovements());
        System.out.println(clientDTO.getFullName() + "'s Loans as borrower:");
        printClientLoansInfo(clientDTO.getLoansAsBorrower());
        System.out.println(clientDTO.getFullName() + "'s Loans as giver:");
        printClientLoansInfo(clientDTO.getLoansAsGiver());
    }

    public void printClientLoansInfo(List<LoanDTO> loanDTOList) {
        if (loanDTOList.isEmpty())
            System.out.println("This list is empty right now\n");
        else {
            for (LoanDTO loanDTO : loanDTOList) {
                System.out.println("Name: " + loanDTO.getLoansID());
                System.out.println("Category: " + loanDTO.getCategory());
                System.out.println("Original amount: " + loanDTO.getOriginalAmount());
                System.out.println("Rate of payments: one payment every " + loanDTO.getPace() + " time unit");
                System.out.println("Intereset for any payment: " + loanDTO.getInterestRate());
                double interestPrecentage = (double) loanDTO.getInterestRate() / 100.0;
                System.out.println("Final amount paying back: " + loanDTO.getOriginalAmount() * (1 + interestPrecentage));
                System.out.println("Loan status: " + loanDTO.getStatus());
                printLittleInfoByStatus(loanDTO, loanDTO.getStatus());
            }
        }
    }

    public void printLittleInfoByStatus(LoanDTO loanDTO, Status status) {
        switch (status) {
            case ACTIVE:
                System.out.println("Next payment at time: " + loanDTO.getNextPaymentTime() + ", amount: " + loanDTO.getTotalAmountPerPayment());
                break;
            case PENDING:
                System.out.println("Missing amount to make this loan active: " + (loanDTO.getOriginalAmount() - loanDTO.getAmountCollected()));
                break;
            case FINISHED:
                System.out.println("First payment time: " + loanDTO.getFirstPaymentTime() + ", last payment time: " + loanDTO.getLastPaymentTime() + ".");
                break;
            case RISK:
            int sumMissingPayments=(int)loanDTO.sumMissingPayments();
                System.out.println("So far " + loanDTO.getAmountPaidBack() + " NIS have been paid and "
                        + sumMissingPayments*loanDTO.getTotalAmountPerPayment() + " are missing, in " + sumMissingPayments + " payments");
                break;
        }
        System.out.println("");
    }

    public void printAllMovements(Map<Integer, List<MovementDTO>> movements) {
        if (movements.isEmpty())
            System.out.println("There are no movements in account right now\n");
        else {
            Collection<List<MovementDTO>> movementsByTime = movements.values();
            System.out.println("Account movements:");
            for (List<MovementDTO> set : movementsByTime) {
                for (MovementDTO movement : set) {
                    printOneMovementInfo(movement);
                }
            }
            System.out.println("");
        }
    }

    public void printOneMovementInfo(MovementDTO movement) {
        System.out.println("Movement time: " + movement.getExecuteTime());
        System.out.println("Account balance before movement: " + movement.getAmountBeforeMovement());
        System.out.println(movement.getKindOfExecute() + movement.getAmount() + " NIS");
        System.out.println("Account balance after movement: " + movement.getAmountAfterMovement() + "\n");
    }

}