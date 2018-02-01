package com.czetus.main;

public class MyMessage
{
    private String temat;
    private String odbiorca;
    private String nadawca;

    private MyMessage(String temat, String odbiorca, String nadawaca){
        this.temat = temat;
        this.odbiorca = odbiorca;
        this.nadawca = nadawaca;
    }

    public static MessageBuilder builder(){
        return new MessageBuilder();
    }
    public String getTemat()
    {
        return temat;
    }

    public void setTemat(String temat)
    {
        this.temat = temat;
    }

    public String getOdbiorca()
    {
        return odbiorca;
    }

    public void setOdbiorca(String odbiorca)
    {
        this.odbiorca = odbiorca;
    }

    public String getNadawca()
    {
        return nadawca;
    }

    public void setNadawca(String nadawca)
    {
        this.nadawca = nadawca;
    }


    public static class MessageBuilder
    {
      /*  private MyMessage myMessage; */
      private String temat;
      private String odbiorca;
       private String nadawca;

        public MessageBuilder()
        {
        }

       public MessageBuilder temat(String temat)
        {
            this.temat = temat;
            return this;
        }

        public MessageBuilder nadawca(String nadawca)
        {
            this.nadawca = nadawca;
            return this;
        }

        public MessageBuilder odbiorca(String odbiorca)
        {
            this.odbiorca  = odbiorca;
            return this;
        }

        public MyMessage build()
        {
          return  new MyMessage(this.temat,this.odbiorca,this.nadawca);
        }

    }


}
