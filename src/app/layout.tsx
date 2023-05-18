"use client";

import "bootstrap/dist/css/bootstrap.min.css";
import Navbar from "@/components/Navbar";
import UserProvider from "@/contexts/User";
import LoginsProvider from "@/contexts/Logins";
import { useEffect, useState } from "react";

export const metadata = {
  title: "Password Manager",
  description: "Password Manager",
};

export default function RootLayout({ children }: React.PropsWithChildren) {
  const [user, userSet] = useState(null);
  const [logins, loginsSet] = useState(null);

  useEffect(() => {
    fetch("/userdetails", {
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
    })
      .then((response) => response.json())
      .then((data) => {
        fetch(`/user/${data.user._id}/login`, {
          headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
          },
        })
          .then((response) => response.json())
          .then(loginsSet);

        userSet(data);
      });
  }, []);

  return (
    <html lang="en">
      <body>
        <UserProvider state={[user, userSet]}>
          <LoginsProvider state={[logins, loginsSet]}>
            <header>
              <Navbar />
            </header>

            <main className="pt-3">{children}</main>
          </LoginsProvider>
        </UserProvider>
      </body>
    </html>
  );
}