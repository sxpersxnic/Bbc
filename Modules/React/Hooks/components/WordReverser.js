import React,{ useState } from "react";
import styles from "./WordReverser.module.css";

const WordReverser = () => {

    const [inputWord, setInputWord] = useState('');
    const [displayedWord, setDisplayedWord] = useState('');

    const handleKeyPress = (e) => {
        if (e.key === 'Enter') {
          setDisplayedWord(inputWord.trim() !== '' ? inputWord : '');
        }
    };
        
    const toggleReverse = () => {
        setDisplayedWord((prevWord) => (prevWord.trim() !== '' ? prevWord.split('').reverse().join('') : ''));
    };

    return(
        <div>
            <input type="text" placeholder="enter word" value={inputWord} onChange={(e) => setInputWord(e.target.value)} onKeyPress={handleKeyPress}/>
            <div className={styles.container}>{displayedWord && <p>{displayedWord}</p>}</div>
            <button className={styles.button} onClick={toggleReverse}>reverse</button>
        </div>
    );
}

export default WordReverser;