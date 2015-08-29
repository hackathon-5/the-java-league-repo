package HolyGhost;
use Mojo::Base 'Mojolicious';
use Mojo::Home;
use Mojo::IOLoop;
use HolyGhost::DB::Schema;
use Data::Dumper;

has home => sub {
   return Mojo::Home->new->detect;
};

has db => sub {
   my $self       = shift;
   my $dbhost     = $self->config->{dbhost};
   my $dbport     = $self->config->{dbport};
   my $dbname     = $self->config->{dbname};
   my $dbuser     = $self->config->{dbuser};
   my $dbpassword = $self->config->{dbpassword};
   my $schema = HolyGhost::DB::Schema->connect(
      "dbi:Pg:dbname=$dbname;host=$dbhost;port=$dbport",
      $dbuser,
      $dbpassword,
      { pg_enable_utf8 => 1, RaiseError => 1 }
   ) or die "failed to connect to database\n";
   return $schema;
};

sub load_config {
   my $self = shift;
   my $configfile = $self->home->rel_file('../config/config.pl');

   $self->plugin(Config => {
      file => $configfile,
      default => {
         dbhost     => 'localhost',
         dbport     => 5432,
         dbname     => 'holyghost',
         dbuser     => 'holyghost',
         dbpassword => 'holyghost'
      }
   });
}

sub startup {
   my $self = shift;

   $self->load_config;

   $self->secrets(['abcdef']);

   $self->helper(db => sub { shift->app->db });

   $self->helper(log_error => sub {
      my ($self, $err) = @_;
      print "[ERROR] $err\n";
   });

   $self->helper(not_found => sub {
      shift->render(json => {error => 'not found'}, status => 404);
   });

   $self->helper(get_user => sub {
      my $self = shift;
      my $id = $self->session('user_id');

      return undef unless $id;
      return $self->db->resultset('Account')->single({id => $id});
   });

   my $r = $self->routes;
   $r->post('/login')->to('account#login');
   $r->any('/logout')->to('account#logout');
   $r->get('/locations')->to('location#get_all');
   $r->get('/locations/:id' => [id => qr/\d+/])->to('location#get');

   my $if_authed = $r->under('/' => sub {
      my $c = shift;
      return 1 if $c->get_user;
      $c->render(json => {error => 'not authorized'}, status => 401);
      return undef;
   });

   $if_authed->get('/users/:id' => [id => qr/\d+/])->to('account#get');
   $if_authed->get('/sightings')->to('sighting#get_all');
   $if_authed->get('/sightings/:id' => [id => qr/\d+/])->to('sighting#get');
   $if_authed->post('/sightings')->to('sighting#create');

   $r->any('*' => sub {
      shift->not_found;
   });
}

1;
