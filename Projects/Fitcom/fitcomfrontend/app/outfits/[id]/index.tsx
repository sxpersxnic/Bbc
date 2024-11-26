import { useRouter } from "expo-router";
import { SafeAreaView, StatusBar, View } from "react-native";
import Header from "../../../components/Header";
import IconButton from "../../../components/IconButton";
import OutfitDetails from "../../../components/OutfitDetails";



export default function OutfitDetailsPage() {
    const router = useRouter();

    return (
        <SafeAreaView style={{ flex: 1 }}>
            <StatusBar backgroundColor={"white"} />
            <View style={{ flexDirection: "row", justifyContent: "space-between" }}>
                <Header>FitCom</Header>
                <IconButton onPress={() => router.push('/')} iconName='home' iconSize={24} style={{ backgroundColor: "none" }} variant='surface' />

            </View>

            <View>
                <OutfitDetails />
            </View>

        </SafeAreaView>
    )
}