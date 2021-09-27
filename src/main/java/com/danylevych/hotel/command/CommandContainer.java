package com.danylevych.hotel.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class CommandContainer {

    private static final Map<String, Command> COMMANDS = new HashMap<>();

    static {
	COMMANDS.put("setLocale", new SetLocaleCommand());
	COMMANDS.put("signOut", new SignOutCommand());
	COMMANDS.put("answer", new AnswerCommand());
	COMMANDS.put("order", new OrderCommand());
	COMMANDS.put("login", new LoginCommand());
	COMMANDS.put("list", new ListCommand());
	COMMANDS.put("book", new BookCommand());
	COMMANDS.put("cart", new CartCommand());
    }

    private CommandContainer() {

    }

    public static String execute(HttpServletRequest request,
            HttpServletResponse response) {
	return get(request).execute(request, response);
    }

    private static Command get(HttpServletRequest request) {
	return COMMANDS.get(request.getParameter("action"));
    }

}
