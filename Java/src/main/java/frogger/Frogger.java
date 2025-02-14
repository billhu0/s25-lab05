package frogger;

/**
 * Refactor Task 1 & 2: Frogger
 *
 * @author Zishen Wen (F22), Deyuan Chen (S22), Duan Liang (F23)
 */
public class Frogger {

    // Field for task 1.
    private final Road road;
    private int position;
    
    // Field for task 2. Anything to add/change?
    private final Records records;
    // private String firstName, lastName, phoneNumber, zipCode, state, gender;
    private final FroggerID identity;

    // Answer: Frogger class has too many identity-related fields.
    // There's a poor cohesion between frog movement and identity information.
    // Therefore, we can create a record class to store identity information, which are not required to the movement.

    // public Frogger(Road road, int position, Records records, String firstName, String lastName, String phoneNumber,
    // String zipCode, String state, String gender) {
    //     this.road = road;
    //     this.position = position;
    //     this.records = records;
    //     this.firstName = firstName;
    //     this.lastName = lastName;
    //     this.phoneNumber = phoneNumber;
    //     this.zipCode = zipCode;
    //     this.state = state;
    //     this.gender = gender;
    // }

    public Frogger(Road road, int position, Records records, FroggerID identity) {
        this.road = road;
        this.position = position;
        this.records = records;
        this.identity = identity;
    }

    /**
     * Moves Frogger.
     *
     * @param forward true is move forward, else false.
     * @return true if move successful, else false.
     */
    public boolean move(boolean forward) {
        int nextPosition = this.position + (forward ? 1 : -1);
        if (!road.isValidPosition(nextPosition) || road.isOccupied(nextPosition)) {
            return false;
        }
        this.position = nextPosition;
        return true;
    }

    // TODO: Do you notice any issues here?
    // Answer: Frogger class is too dependent on Road's internal implementation.
    // This means we are not distributing knowledge (responsibility) properly.
    // Fix: move that logic to Road class.
    
    // Below is the original code:
    // It has been deleted.
    // public boolean isOccupied(int position) {
    //     boolean[] occupied = this.road.getOccupied();
    //     return occupied[position];
    // }
    
    // Answer: move that logic to Road class.
    // Because it is not the responsibility of Frogger to know if a position is occupied.

    // Below is the original code:
    // It has been deleted.
    // public boolean isValid(int position) {
    //     if (position < 0) return false;
    //     boolean[] occupied = this.road.getOccupied();
    //     return position < occupied.length;
    // }

    /**
     * Records Frogger to the list of records.
     * 
     * @return true if record successful, else false.
     */
    // Answer: modified accordingly.
    public boolean recordMyself() {
    //   boolean success = records.addRecord(firstName, lastName, phoneNumber, zipCode, state, gender);
    //   return success;
        return records.addRecord(
            identity.firstName(),
            identity.lastName(),
            identity.phoneNumber(),
            identity.zipCode(),
            identity.state(),
            identity.gender()
        );
    }

}
