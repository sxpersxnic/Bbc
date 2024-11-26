import React, { useState } from "react";
import styles from "./ShoppingList.module.css";

export default function ShoppingList({ title, initialItems = [] }) {

    const [items, setItems] = useState(initialItems);
    const [newItemName, setNewItemName] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    const handleAddItem = () => {

        if (newItemName.trim() === '') {
            setErrorMessage("items name cant be empty!");
            return;
        };

        const newItem = {
            id: items.length + 1,
            name: newItemName.trim(),
        };

        setItems([...items, newItem]);
        setNewItemName('');
        setErrorMessage('');

    };

    const handleRemoveItem = (itemId) => {
        const updatedItems = items.filter((item) => item.id !== itemId);
        setItems(updatedItems);
    };

    const handleClearList = (itemId) => {
        const updatedItems = items.filter((item) => item.id === itemId);
        setItems(updatedItems);
    };

    return(
        <div>
            <h2 className={styles.title}>{title}</h2>
            <p className={styles.text}>List has {items.length} items</p>
            <ul>
                {items.length === 0 ? (
                    <p></p>
                ) : (
                    items.map((item) => (
                        
                        <li key={item.id}>
                            {item.name}
                            <button onClick={() => handleRemoveItem(item.id)} className={styles.removeButton}>x</button>
                        </li>
                        
                    ))
                
                )}
            </ul>
            <div>
                <input
                    type="text"
                    value={newItemName}
                    onChange={(e) => setNewItemName(e.target.value)}
                    placeholder="Add new item"
                />
                <button onClick={handleAddItem} className={styles.addButton}>Add</button>
                <button onClick={handleClearList} className={styles.clearListButton}>clear list</button>
            </div>
            {errorMessage && <p className={styles.error}>{errorMessage}</p>}
        </div>
    );
};