import Button from "../components/Button"
import Header from "../components/Header"
import WordReverser from "../components/Wordreverser"
import ShoppingList from "../components/Shoppinglist"
import Thumbnail from "../components/Thumbnail"

import styles from "./index.module.css"

export default function IndexPage() {

    const centerContent = styles.center;
    const seperateContent = styles.seperate;

    const shoppingListItems = [
        { name: "Milk", amount: 2 },
        { name: "Eggs", amount: 6 },
        { name: "Bread", amount: 1 }
      ];
      

    return (

        <div>
            <div>
                <Header title="Buttons" subtitle="dynamic buttons"/>

                <div className={`${centerContent}`}>
                    <Button text="Delete" variant="delete"/>
                    <Button text="Default" variant="default"/>
                    <Button text="Create" variant="create"/>
                </div>
            </div>

            <div className={`${seperateContent}`}></div>

            <div>
                <Header title="Wordreverser" subtitle="reversed words"/>

                <div className={`${centerContent}`}>
                    <WordReverser word="Hello world"/>
                </div>
            </div>

            <div className={`${seperateContent}`}></div>

            <div>
                <Header title="Shoppinglist" subtitle="Shopping List"/>

                <div className={`${centerContent}`}>
                    <ShoppingList title="Breakfast Shopping List" items={shoppingListItems}/>
                </div>
            </div>

            <div className={`${seperateContent}`}></div>

            <div>
                <Header title="Thumbnail" subtitle="thumbnail component"/>

                <div className={`${centerContent}`}>
                    <Thumbnail title="Learning react" views="123321" channelName="zkampm coding" desciption="Follow me" img="https://via.placeholder.com/360x200"/>
                </div>
            </div>
            

        </div>
    );
}