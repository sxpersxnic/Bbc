import React, { useState } from "react";
import styles from "./Counter.module.css";

const Counter = () => {

    const [count, setCounter] = useState(0);

    const handleIncrement = () => {
        setCounter(count + 1);
    }

    const handleDecrement = () => {
        setCounter(count - 1);
    }

    return(
        <div className={styles.counterContainer}>
            <button onClick={handleDecrement} className={styles.decreaseButton}>-</button>
            <span className={styles.number}>{count}</span>
            <button onClick={handleIncrement} className={styles.increaseButton}>+</button>
        </div>
    );
};

export default Counter;