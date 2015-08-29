package HolyGhost::Account;
use Mojo::Base 'Mojolicious::Controller';

sub get {
   my $self = shift;
   my $id = $self->param('id');

   my $user = $self->db->resultset('Account')->search({id => $id}, {columns => [qw/id username realname/]})->single;
   if ($user) {
      my %cols = $user->get_columns;
      $self->render(json => \%cols);
   } else {
      $self->not_found;
   }
}

sub login {
   my $self = shift;
   my $username = $self->param('username');
   my $password = $self->param('password');
   my $db = $self->db;

   my $user = $db->resultset('Account')->single({username => $username});
   if ($user and $user->check_password($password)) {
      $self->session(user_id => $user->id);
      $self->session(username => $username);
      $self->render(json => {message => 'login successful'}, status => 200);
   } else {
      $self->render(json => {error => 'bad username/password'}, status => 401);
   }
}

sub logout {
   shift->session(expires => 1);
}

1;
