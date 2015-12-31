<%@ Page Title="Home Page" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true"
    CodeBehind="CardaLog.aspx.cs" Inherits="cards_web_interface._Default" %>

<asp:Content ID="HeaderContent" runat="server" ContentPlaceHolderID="HeadContent">
</asp:Content>
<asp:Content ID="BodyContent" runat="server" ContentPlaceHolderID="MainContent">
    <div id="main">
    <div id="content" style="min-height: 250px; height: 355px;">
        Manufacturer<br />
        <asp:DropDownList ID="DDLMfg" runat="server" 
            onselectedindexchanged="DDLMfg_SelectedIndexChanged" 
            AutoPostBack="True">
        </asp:DropDownList>
        <br />
        Year<br />
        <asp:DropDownList ID="DDLYear" runat="server" AutoPostBack="True" 
            onselectedindexchanged="DDLYear_SelectedIndexChanged">
        </asp:DropDownList>
        <br />
        Player Name<br />
        <asp:DropDownList ID="DDLPlayerName" runat="server" AutoPostBack="True" 
            onselectedindexchanged="DDLPlayerName_SelectedIndexChanged">
        </asp:DropDownList>
        <br />
        Card Number<br />
        <asp:DropDownList ID="DDLCardNumber" runat="server" AutoPostBack="True" 
            onselectedindexchanged="DDLCardNumber_SelectedIndexChanged">
        </asp:DropDownList>

    <asp:GridView ID="GridView1" runat="server" BackColor="White" 
        BorderColor="#E7E7FF" BorderStyle="None" BorderWidth="1px" CellPadding="3" 
        GridLines="Horizontal" 
        onselectedindexchanged="GridView1_SelectedIndexChanged" AllowPaging="True" 
        onpageindexchanging="GridView1_PageIndexChanging" PageIndex="1">
        <AlternatingRowStyle BackColor="#F7F7F7" />
        <FooterStyle BackColor="#B5C7DE" ForeColor="#4A3C8C" />
        <HeaderStyle BackColor="#4A3C8C" Font-Bold="True" ForeColor="#F7F7F7" />
        <PagerStyle BackColor="#E7E7FF" ForeColor="#4A3C8C" HorizontalAlign="Right" />
        <RowStyle BackColor="#E7E7FF" ForeColor="#4A3C8C" />
        <SelectedRowStyle BackColor="#738A9C" Font-Bold="True" ForeColor="#F7F7F7" />
        <SortedAscendingCellStyle BackColor="#F4F4FD" />
        <SortedAscendingHeaderStyle BackColor="#5A4C9D" />
        <SortedDescendingCellStyle BackColor="#D8D8F0" />
        <SortedDescendingHeaderStyle BackColor="#3E3277" />
    </asp:GridView>
    <br />
    </div>
    <br />
    <br />
    </div>
</asp:Content>