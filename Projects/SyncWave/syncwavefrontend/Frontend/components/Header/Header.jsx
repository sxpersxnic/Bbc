import {Image} from "primereact/image";
import {Button} from "primereact/button";
import Logo from '../../resources/images/syncwaveLogo.svg';
import {useSession} from "../../lib/session";

export default function Header() {

    const {signOut} = useSession();

  return (
      <div className="card flex flex-row justify-content-between align-items-center p-2 surface-card border-noround">
          <Image src={Logo.src} width="60px"/>
          <h1>SyncWave</h1>
          <Button
              className="text-center mt-2"
              label="Logout"
              onClick={() => {
                  signOut();
              }}
          />
      </div>

  );
}
