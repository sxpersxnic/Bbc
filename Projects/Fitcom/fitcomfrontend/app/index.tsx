import { useRouter } from 'expo-router';
import registerRootComponent from 'expo/build/launch/registerRootComponent';
import { SafeAreaView, StatusBar, View } from 'react-native';
import CustomButton from '../components/CustomButton';
import Header from "../components/Header";
import IconButton from '../components/IconButton';

export default function Home() {
  const router = useRouter();

  return (
    <SafeAreaView style={{ flex: 1 }}>
      <StatusBar backgroundColor={"white"} />
      <View style={{ flexDirection: "row", justifyContent: "space-between" }}>
        <Header>FitCom</Header>
        <IconButton onPress={() => router.push('/user/register')} iconName='account-circle' iconSize={24} style={{ backgroundColor: "none" }} variant='surface' />
      </View>

      <View>
        <CustomButton onPress={() => router.push('/outfits/create')}>outfits/create</CustomButton>
        <CustomButton onPress={() => router.push('/garments/create')}>garments/create</CustomButton>
        <CustomButton onPress={() => router.push('/garments/')}>garmentPage</CustomButton>
        <CustomButton onPress={() => router.push('/outfits/')}>outfitPage</CustomButton>
      </View>
    </SafeAreaView>
  )
}


registerRootComponent(Home);
