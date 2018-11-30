package cs361.battleships.models;

public enum AtackStatus {

	/**
	 * The result if an attack results in a miss.
	 */
	MISS,

	/**
	 * The result if an attack results in a miss.
	 */
	MISSLASER,

	/**
	 * The result if an attack results in a hit on an enemy ship.
	 */
	HIT,

	/**
	 * The result if an attack results in a hit on an enemy ship.
	 */
	HITLASER,

    /**
     * The result if the captains quarters is hit
     */
    CAPTAIN,

    /**
     * The result if the captains quarters is hit
     */
    CAPTAINLASER,

	/**
	 * THe result if an attack sinks the enemy ship
	 */
	SUNK,

	/**
	 * THe result if an attack sinks the enemy ship
	 */
	SUNKLASER,

	/**
	 * The results if an attack results in the defeat of the opponent (a
	 * surrender).
	 */
	SURRENDER,
	
	/**
	 * The result if the coordinates given are invalid.
	 */
	INVALID,

}
