type LoginFormProps = {
  email: string;
  pin: string;
  handleLogin: (event: React.FormEvent<HTMLFormElement>) => void;
  setEmail: (value: string) => void;
  setPin: (value: string) => void;
};

const LoginForm = ({ email, pin, handleLogin, setEmail, setPin }: LoginFormProps) => {
  return (
    <form onSubmit={handleLogin}>
      <label  htmlFor="email">Email</label>
      <input
        type="text"
        id="email"
        value={email}
        name="email"
        onChange={({ target }) => setEmail(target.value)}
      />
      <label htmlFor="pin">PIN</label>
      <input
        type="password"
        id="pin"
        value={pin}
        name="pin"
        onChange={({ target }) => setPin(target.value)}
      />
      <button type="submit">Login</button>
    </form>
  )
}

export default LoginForm;