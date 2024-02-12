import { Outlet } from 'react-router-dom'
import { AuthenticationContext } from './shared/state/context';
import { Provider } from 'react-redux';
import { store } from './shared/state/redux';
import { NavBar } from './shared/components/navbar';

function App() {

  return (
    <AuthenticationContext>
      <Provider store={store}>
        <NavBar/>
        <div className='container mt-3'>       
          <Outlet />
        </div>
      </Provider>
    </AuthenticationContext>
  );
}

export default App
