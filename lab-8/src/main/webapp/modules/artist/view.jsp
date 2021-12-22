<div>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
            </tr>
        </thead>
        <tbody>
            <%= "<tr><td>1</td><td>No name</td></tr>" %>
        <%
            var array = new String[3];
            array[0] = "Test1";
            array[1] = "Test2";
            array[2] = "Test3";
            int i = 0;
            while (i++ < 3) {%>
                <tr>
                    <td><%=i%></td>
                    <td><%=array[i]%></td>
                </tr>
        <%}%>
        </tbody>
    </table>
</div>
